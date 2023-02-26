package com.vivid.apiserver.domain.individual_video.domain;

import com.vivid.apiserver.domain.user.domain.User;
import com.vivid.apiserver.domain.user.domain.AccountBuilder;
import com.vivid.apiserver.domain.video.domain.Video;
import com.vivid.apiserver.domain.video.domain.VideoBuilder;

public class IndividualVideoBuilder {

    public static IndividualVideo build() {

        Video video = VideoBuilder.build();
        User user = AccountBuilder.build();

        IndividualVideo individualVideo = IndividualVideo.builder()
                .video(video).build();

        return individualVideo;
    }

    public static IndividualVideo build(User user, Video video) {

        IndividualVideo individualVideo = IndividualVideo.builder()
                .video(video).build();

        return individualVideo;
    }

}