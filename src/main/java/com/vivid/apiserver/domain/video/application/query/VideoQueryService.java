package com.vivid.apiserver.domain.video.application.query;

import com.vivid.apiserver.domain.video.dao.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoQueryService {

    private final VideoRepository videoRepository;


}
