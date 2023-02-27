package com.vivid.apiserver.domain.video_space.application.command;

import com.vivid.apiserver.domain.video_space.dao.VideoSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VideoSpaceCommandService {

    private final VideoSpaceRepository videoSpaceRepository;

}
