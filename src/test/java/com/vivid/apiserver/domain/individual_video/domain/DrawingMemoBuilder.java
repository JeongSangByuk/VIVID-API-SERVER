package com.vivid.apiserver.domain.individual_video.domain;

import java.time.LocalTime;

public class DrawingMemoBuilder {

    public static LocalTime START_TIME = LocalTime.of(00,12,00);
    public static Integer DURATION = 30;
    public static String FILE_PATH = "test.aws.com";

    public static DrawingMemo build(IndividualVideo individualVideo){

        DrawingMemo drawingMemo = DrawingMemo.builder().individualVideo(individualVideo)
                .startTime(START_TIME).duration(DURATION).filePath(FILE_PATH).build();

        return drawingMemo;
    }

}
