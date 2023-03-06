package com.vivid.apiserver.global.infra.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.InvalidValueException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * multipart file을 통해 video s3에 업로드하는 메소드
     */
    public void uploadVideoToS3ByMultipartFile(MultipartFile file, Long videoId) {

        ObjectMetadata objectMetadata = createObjectMetadata(file);
        InputStream inputStream = getInputStream(file);

        PutObjectRequest request = new PutObjectRequest(rawVideoBucket, createVideoFileKey(videoId), inputStream,
                objectMetadata);
        request.withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3Client.putObject(request);
    }

    /**
     * download Url를 통해 video s3에 업로드하는 메소드
     */
    public void uploadVideoToS3ByDownloadUrl(String fileUrl, Long videoId) {

        ObjectMetadata metadata = new ObjectMetadata();
        byte[] bytes = getInputStreamByUrl(fileUrl);

        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        PutObjectRequest request = new PutObjectRequest(rawVideoBucket, createVideoFileKey(videoId), byteArrayIs,
                metadata);
        request.withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3Client.putObject(request);
    }

    /**
     * 스냅샷 이미지를 s3에 업로드하는 메소드
     */
    public String uploadSnapshotImagesToS3(MultipartFile file, String individualVideoId, Long videoTime) {

        String snapshotImageKey = createShapshotImageKey(individualVideoId, videoTime);
        ObjectMetadata objectMetadata = createObjectMetadata(file);
        InputStream inputStream = getInputStream(file);

        PutObjectRequest request = new PutObjectRequest(imageSnapshotBucket, snapshotImageKey, inputStream,
                objectMetadata);
        request.withCannedAcl(CannedAccessControlList.Private);

        amazonS3Client.putObject(request);

        return String.valueOf(amazonS3Client.getUrl(imageSnapshotBucket, snapshotImageKey));
    }

    /**
     * video file path를 get하는 메소드
     */
    public String getVideoFilePath(Long videoId) {

        String s3VideoKey = createVideoKey(videoId);
        return amazonS3Client.getUrl(serviceVideoBucket, s3VideoKey).toString();
    }

    /**
     * video의 visual index 이미지 파일 path list get하는 메소드
     */
    public List<String> getVisualIndexImages(Long videoId) {

        List<String> imagePaths = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = createVideoIndexImageGetRequest(videoId);
        ObjectListing objects = amazonS3Client.listObjects(listObjectsRequest);

        while (objects != null) {
            objects = addImages(objects, imagePaths);
        }

        return imagePaths;
    }

    private String createShapshotImageKey(String individualVideoId, Long videoTime) {
        return individualVideoId + '/' + videoTime;
    }

    private String createVideoFileKey(Long videoId) {
        return videoId.toString() + ".mp4";
    }

    private byte[] getInputStreamByUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openConnection().getInputStream();

            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            throw new InvalidValueException(ErrorCode.VIDEO_UPLOAD_FAILED);
        }
    }

    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new InvalidValueException(ErrorCode.VIDEO_UPLOAD_FAILED);
        }
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    private String createVideoKey(Long videoId) {
        return videoId + "/Default/HLS/" + videoId.toString() + ".m3u8";
    }

    private ListObjectsRequest createVideoIndexImageGetRequest(Long videoId) {

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(serviceVideoBucket);
        listObjectsRequest.setPrefix(videoId + "/Default/Thumbnails/");
        listObjectsRequest.setDelimiter("/");
        return listObjectsRequest;
    }

    private ObjectListing addImages(ObjectListing objects, List<String> imagePaths) {

        List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();

        if (objectSummaries.size() < 1) {
            return null;
        }

        for (S3ObjectSummary item : objectSummaries) {

            if (isDirectory(item)) {
                continue;
            }

            imagePaths.add(amazonS3Client.getUrl(serviceVideoBucket, item.getKey()).toString());
        }

        return amazonS3Client.listNextBatchOfObjects(objects);
    }

    private boolean isDirectory(S3ObjectSummary item) {
        return item.getKey().endsWith("/");
    }


}
