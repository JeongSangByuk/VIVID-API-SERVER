package com.vivid.apiserver.domain.video_space.application.command;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantDao;
import com.vivid.apiserver.domain.video_space.dao.VideoSpaceParticipantRepository;
import com.vivid.apiserver.domain.video_space.domain.VideoSpace;
import com.vivid.apiserver.domain.video_space.domain.VideoSpaceParticipant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoSpaceParticipantCommandService {

    private final VideoSpaceParticipantDao videoSpaceParticipantDao;
    private final VideoSpaceParticipantRepository videoSpaceParticipantRepository;

    public VideoSpaceParticipant save(VideoSpace videoSpace, User user) {

        VideoSpaceParticipant videoSpaceParticipant = VideoSpaceParticipant.of(videoSpace, user);

        videoSpaceParticipantRepository.save(videoSpaceParticipant);

        return videoSpaceParticipant;
    }

    public void delete(VideoSpaceParticipant videoSpaceParticipant) {
        videoSpaceParticipantRepository.delete(videoSpaceParticipant);
    }

    public void deleteAll(List<VideoSpaceParticipant> videoSpaceParticipants) {
        videoSpaceParticipantDao.deleteAll(videoSpaceParticipants);
    }

    public void deleteAllByVideoSpace(VideoSpace videoSpace) {
        videoSpaceParticipantDao.deleteAllByVideoSpace(videoSpace);
    }
}
