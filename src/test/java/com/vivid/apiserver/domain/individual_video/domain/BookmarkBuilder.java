package com.vivid.apiserver.domain.individual_video.domain;

import java.time.LocalTime;

public class BookmarkBuilder {

    public static String NAME = "중요 부분!";
    public static String CONTENT = "매우 어렵다!ㅠㅠ 다시보기!";
    public static LocalTime VIDEO_TIME = LocalTime.of(00, 12, 00);

    public static Bookmark build(IndividualVideo individualVideo) {
        Bookmark bookmark = Bookmark.builder().individualVideo(individualVideo)
                .name(NAME).content(CONTENT).videoTime(VIDEO_TIME).build();

        return bookmark;
    }


}
