package tk.withkid.userlog.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtill {
    static public ZonedDateTime startOfDayNow(){
        return LocalDate.now().atStartOfDay(ZoneOffset.UTC);
    }

    static public LocalDateTime nowOfUTC(){
        return LocalDateTime.now(ZoneOffset.UTC);
    }
    static public String oneWeekAgo(){
        return startOfDayNow().minusWeeks(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    static public String tomorrow(){
        return startOfDayNow().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
