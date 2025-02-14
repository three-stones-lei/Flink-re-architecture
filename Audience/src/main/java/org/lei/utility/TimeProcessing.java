package org.lei.utility;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName: TimeProcessing
 * Package: org.lei.utility
 * Description:
 *
 * @Author Lei
 * @Create 16/4/2024 12:07 pm
 * @Version 1.0
 */
public class TimeProcessing {
    public static Long UtcStringToLongType(String timestampStr, String timezone){
        String processedUtcTimestamp = timestampStr.replace(" UTC", "Z").replace(" ", "T");
        Instant timestamp = Instant.parse((CharSequence) processedUtcTimestamp);
        ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.of(timezone));
        return zonedDateTime.toInstant().toEpochMilli();
    }

    public static String UtcStringToLocalDatetimeString(String timestampStr, String timezone){
        String processedUtcTimestamp = timestampStr.replace(" UTC", "Z").replace(" ", "T");
        Instant timestamp = Instant.parse((CharSequence) processedUtcTimestamp);
        ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.of(timezone));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return zonedDateTime.format(formatter);
    }

    public static String UtcStringToLocalDateString(String timestampStr, String timezone){
        String processedUtcTimestamp = timestampStr.replace(" UTC", "Z").replace(" ", "T");
        Instant timestamp = Instant.parse((CharSequence) processedUtcTimestamp);
        ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.of(timezone));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return zonedDateTime.format(formatter);
    }
}

