package com.vivid.apiserver.global.infra.storage;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    void uploadVideoToS3ByMultipartFile(MultipartFile file, Long videoId);

    void uploadVideoToS3ByDownloadUrl(String fileUrl, Long videoId);

    String uploadSnapshotImagesToS3(MultipartFile file, String individualVideoId, Long videoTime);

    String getVideoFilePath(Long videoId);

    List<String> getVisualIndexImages(Long videoId);
}
