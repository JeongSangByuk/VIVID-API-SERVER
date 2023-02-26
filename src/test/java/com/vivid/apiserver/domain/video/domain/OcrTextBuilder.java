package com.vivid.apiserver.domain.video.domain;

import java.time.LocalTime;

public class OcrTextBuilder {

    public static String TEXT = "이것은 매우 어려운 내용이다!";

    public static LocalTime START_TIME = LocalTime.of(00, 12, 00);

    public static Integer DURATION = 30;

    public static OcrText build(Video video) {

        OcrText ocrText = OcrText.builder().video(video).text(TEXT).duration(DURATION).startTime(START_TIME).build();

        return ocrText;

    }
}
