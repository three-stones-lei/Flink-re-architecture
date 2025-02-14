package org.lei;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ClassName: DateFormatUtil
 * Package: org.lei
 * Description:
 *
 * @Author Lei
 * @Create 2/8/2024 9:10 am
 * @Version 1.0
 */
public class DateFormatUtil {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dtfForPartition = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter dtfFull = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dtfFullWithUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");


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

    public static Long UTCdateTimeToTs(String dateTime) {
        String processedUtcTimestamp = dateTime.replace(" UTC", "Z").replace(" ", "T");
        Instant timestamp = Instant.parse((CharSequence) processedUtcTimestamp);
        LocalDateTime localDateTime = LocalDateTime.parse(processedUtcTimestamp, dtfFullWithUTC);
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
    /**
     * 2023-07-05 01:01:01 转成 ms 值
     * @param dateTime
     * @return
     */
    public static Long dateTimeToTs(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtfFull);
        return localDateTime.toInstant(ZoneOffset.of("+11")).toEpochMilli();
    }

    /**
     * 把毫秒值转成 年月日:  2023-07-05
     * @param ts
     * @return
     */
    public static String tsToDate(Long ts) {
        Date dt = new Date(ts);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault());
        return dtf.format(localDateTime);
    }

    /**
     * 把毫秒值转成 年月日时分秒:  2023-07-05 01:01:01
     * @param ts
     * @return
     */
    public static String tsToDateTime(Long ts) {
        Date dt = new Date(ts);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault());
        return dtfFull.format(localDateTime);
    }

    public static String tsToDateForPartition(long ts) {
        Date dt = new Date(ts);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault());
        return dtfForPartition.format(localDateTime);
    }

    /**
     * 把 年月日转成 ts
     * @param date
     * @return
     */
    public static long dateToTs(String date) {
        return dateTimeToTs(date + " 00:00:00");
    }
}
