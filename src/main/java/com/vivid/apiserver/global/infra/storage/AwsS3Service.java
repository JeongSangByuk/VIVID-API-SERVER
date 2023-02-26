package com.vivid.apiserver.global.infra.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.vivid.apiserver.domain.video.dto.VideoSaveResponse;
import com.vivid.apiserver.domain.video.exception.ImageUploadFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.raw.video.bucket}")
    private String rawVideoBucket;

    @Value("${cloud.aws.s3.service.video.bucket}")
    private String serviceVideoBucket;

    @Value("${cloud.aws.s3.image.snapshot.bucket}")
    private String imageSnapshotBucket;

    private final AmazonS3Client amazonS3Client;

    // multipartFile을 통해 video s3에 업로드
    public VideoSaveResponse uploadVideoToS3ByMultipartFile(MultipartFile file, Long videoId) {

        // 메타 데이터 추출
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        // s3 upload
        try(InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(rawVideoBucket, videoId.toString() + ".mp4", inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ImageUploadFailedException();
        }

        return VideoSaveResponse.builder().id(videoId).build();
    }

    // download Url를 통해 video s3에 업로드
    public VideoSaveResponse uploadVideoToS3ByDownloadUrl(String fileUrl, Long videoId) throws IOException {

        // url connection get
        URL url = new URL(fileUrl);
        URLConnection connection =  url.openConnection();

        // download stream get
        InputStream is = connection.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);
        ObjectMetadata metadata = new ObjectMetadata();
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);
        metadata.setContentLength(bytes.length);

        // s3 upload
        amazonS3Client.putObject(new PutObjectRequest(rawVideoBucket, videoId.toString() + ".mp4", byteArrayIs, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return VideoSaveResponse.builder().id(videoId).build();
    }


    // 스냅샷 이미지를 s3에 업로드하는 메소드
    public String uploadSnapshotImagesToS3(MultipartFile file, String individualVideoId, Long videoTime){

        // 디렉토리 이름은 individualVideoId, fileName은 캡처 시간대
        String snapshotImageKey = individualVideoId + '/' + videoTime;

        // 메타 데이터 추출
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        // s3 upload
        try(InputStream inputStream = file.getInputStream()) {
             amazonS3Client.putObject(new PutObjectRequest(imageSnapshotBucket, snapshotImageKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.Private));
        } catch(IOException e) {
            throw new ImageUploadFailedException();
        }

        // file path get
        String snapshotImageFilePath = String.valueOf(amazonS3Client.getUrl(imageSnapshotBucket, snapshotImageKey));

        return snapshotImageFilePath;
    }

    // video의 m3u8 파일 path get
    public String getVideoFilePath(Long videoId) throws IOException {

        // video key
        String s3VideoKey = videoId + "/Default/HLS/" + videoId.toString() + ".m3u8";
        URL videoFileUrl = amazonS3Client.getUrl(serviceVideoBucket, s3VideoKey);

        return videoFileUrl.toString();
    }


    // video의 visualIndex 이미지 파일 path list get
    public List<String> getVisualIndexImages(Long videoId) throws IOException {

        List<String> keys = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(serviceVideoBucket);
        listObjectsRequest.setPrefix(videoId + "/Default/Thumbnails/");
        listObjectsRequest.setDelimiter("/");

        ObjectListing objects = amazonS3Client.listObjects(listObjectsRequest);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();

            // size 0일 경우
            if (objectSummaries.size() < 1) {
                break;
            }

            for (S3ObjectSummary item : objectSummaries) {

                // 디렉터리가 아니라면
                if (!item.getKey().endsWith("/"))

                    keys.add(amazonS3Client.getUrl(serviceVideoBucket, item.getKey()).toString());
            }

            objects = amazonS3Client.listNextBatchOfObjects(objects);
        }

        return keys;
    }

}
