package tecorb.hafijur.calendar.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.util.Calendar;

public class HRDateUtil {

    /**
     * This static method is used to get the restriction for the max show date*
     * @return
     */
    public static LocalDate getMaxShowDate() {
        return getTodaysDate().plusMonths(12);
    }
    
    public static LocalDate getTodaysDate() {
        return LocalDate.now();
    }
    
    public static LocalDate getConvertedLocalDate(Calendar calendar) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(calendar.getTimeZone().getID());
        return new DateTime(calendar.getTimeInMillis(), dateTimeZone).toLocalDate();
    }

    public static boolean localDateEquals(LocalDate calendar, LocalDate localDate) {
        return !(calendar == null || localDate == null) &&
                calendar.toString().equals(localDate.toString());
    }
}
