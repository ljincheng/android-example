package cn.booktable.note.viewmodel.converter;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.booktable.uikit.util.StringHelper;

public class DateConverter {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Date timeToDate(String value) {
        if (StringHelper.isNotBlank(value)) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               // df.setTimeZone(TimeZone.getTimeZone("GMT"));
//                return OffsetDateTime.parse(value, formatter);
                return df.parse(value);
            } catch (Exception e) {
                Log.e(DateConverter.class.getSimpleName(),"值:"+value+","+ e.getMessage());
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTime(Date value) {
        if (value != null) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //df.setTimeZone(TimeZone.getTimeZone("GMT"));
                return df.format(value);
            }catch (Exception ex)
            {
                Log.e(DateConverter.class.getSimpleName(), "值:"+value+","+ex.getMessage());
            }
            return  null;
        } else {
            return null;
        }
    }

//    @TypeConverter
//    public static Date fromTimestamp(Long value) {
//        return value == null ? null : new Date(value);
//    }
//
//    @TypeConverter
//    public static Long dateToTimestamp(Date date) {
//        return date == null ? null : date.getTime();
//    }
}