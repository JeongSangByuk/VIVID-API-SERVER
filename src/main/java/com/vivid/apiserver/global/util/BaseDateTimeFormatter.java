package com.vivid.apiserver.global.util;

import java.nio.ByteBuffer;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public class BaseDateTimeFormatter {

    public static DateTimeFormatter getLocalTimeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    public static DateTimeFormatter getLocalDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .withLocale( Locale.KOREA )
                .withZone(ZoneId.systemDefault());
    }

    // 10자리의 UUID 생성
    public static String makeShortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        String str = Long.toString(l, Character.MAX_RADIX);

        return str;
    }
}
