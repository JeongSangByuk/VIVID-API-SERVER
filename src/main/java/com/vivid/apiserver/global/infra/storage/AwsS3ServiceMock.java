package com.vivid.apiserver.global.infra.storage;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsS3ServiceMock implements AwsS3Service {

    @Override
    public void uploadVideoToS3ByMultipartFile(MultipartFile file, Long videoId) {

    }

    @Override
    public void uploadVideoToS3ByDownloadUrl(String fileUrl, Long videoId) {

    }

    @Override
    public String uploadSnapshotImagesToS3(MultipartFile file, String individualVideoId, Long videoTime) {
        return "test image path";
    }

    @Override
    public String getVideoFilePath(Long videoId) {
        return "test image path";
    }

    @Override
    public List<String> getVisualIndexImages(Long videoId) {
        return List.of("test01", "test02");
    }
}
