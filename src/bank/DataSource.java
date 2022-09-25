package bank;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataSource {
	
	Calendar cal = Calendar.getInstance();

    String year = cal.get(Calendar.YEAR) + "."; // constant(final) = UPPER CASE ex) Integer.MAX_VALUE
    String month = cal.get(Calendar.MONTH) + 1 + "."; // (0 ~ 11) + 1
    String date = cal.get(Calendar.DATE) + "";

    SimpleDateFormat time = new SimpleDateFormat("hh시 mm분 ss초");
    String curTime = time.format(new Date());

    String[] week = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    String day = week[Calendar.DAY_OF_WEEK - 1];

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getCurTime() {
        return curTime;
    }

    public String getDay() {
        return day;
    }

}
