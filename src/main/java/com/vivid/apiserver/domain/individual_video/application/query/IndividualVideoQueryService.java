package com.vivid.apiserver.domain.individual_video.application.query;

import com.vivid.apiserver.domain.individual_video.dao.repository.IndividualVideoRepository;
import com.vivid.apiserver.domain.individual_video.domain.IndividualVideo;
import com.vivid.apiserver.global.error.exception.ErrorCode;
import com.vivid.apiserver.global.error.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IndividualVideoQueryService {

    private final IndividualVideoRepository individualVideoRepository;

    public IndividualVideo findById(String individualVideoId) {

        UUID uuid = UUID.fromString(individualVideoId);

        return individualVideoRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INDIVIDUAL_VIDEO_NOT_FOUND));
    }
}
