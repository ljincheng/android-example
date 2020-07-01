package cn.booktable.uikit.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public  static String YYYY_MM_DD="yyyy-MM-dd";
    public  static String YYYY_MM_DD_HH_mm_ss="yyyy-MM-dd HH:mm:ss";


    public static String format(Date date)
    {
        return format(date,YYYY_MM_DD);
    }

    public static String format(Date date,String pattern)
    {
        return date==null?null:new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 转为日期
     * @param date
     * @return
     */
    public static Date parseDate(Date date)
    {
        if(date==null)
        {
            return null;
        }
        try {
            return new SimpleDateFormat(YYYY_MM_DD).parse(format(date));
        }catch (ParseException ex)
        {
            ex.printStackTrace();
           throw new RuntimeException(ex);
        }
    }

    public static String formatFullTime(Date date)
    {
        return format(date,YYYY_MM_DD_HH_mm_ss);
    }

    public static String nowSuitableFormat(Date date)
    {
        if(date==null)
        {
            return null;
        }
        String result=null;
        Date now=new Date();
        String nowStr=format(now);
        String dateStr=format(date);
        String timeStr=format(date,"HH:mm");
        if("00:00".equals(timeStr))
        {
            timeStr="";
        }
        if(nowStr.equals(dateStr))
        {
                result = "今日" + timeStr;
        }else{
            long diffMill=date.getTime()-now.getTime();
            int diffDay=(int)(diffMill/(24 * 60 *60 *1000));
            if(diffDay==1)
            {
                result ="明天 "+timeStr;
            }else if(diffDay==0)
            {
                result =timeStr;
            }else if(diffDay==2)
            {
                result="后天"+timeStr;
            }else if(diffDay==-1)
            {
                result="昨天"+timeStr;
            }else{
                if(StringHelper.isBlank(timeStr))
                {
                    result = dateStr;
                }else {
                    result = dateStr + " " + timeStr;
                }
            }

        }
        return result;
    }

    public static String nowSuitableFormat(Date startdate,Date endDate)
    {
        if(startdate==null || endDate==null)
        {
            return null;
        }

        String result=nowSuitableFormat(startdate);
        Date now=new Date();
        String nowStr=format(now);
        String startDateStr=format(startdate);
        String endDateStr=format(endDate);
        String endTimeStr=format(endDate,"HH:mm");
        if("00:00".equals(endTimeStr))
        {
            endTimeStr="";
        }
        if(startdate.compareTo(endDate)==0)
        {
            result=nowSuitableFormat(startdate);
        }else{
            result=nowSuitableFormat(startdate);
            long diffMill=endDate.getTime()-now.getTime();
            int diffDay=(int)(diffMill/(24 * 60 *60 *1000));
            if(nowStr.equals(startDateStr))
            {
                if(diffDay==0)
                {
                    if(StringHelper.isNotBlank(endTimeStr))
                    {

                        result+="至"+endTimeStr;
                    }
                }else if(diffDay==1)
                {
                    result+="至明天"+endTimeStr;
                }else if (diffDay==2)
                {
                    result+="至后天"+endTimeStr;
                }else {
                    if(StringHelper.isBlank(endTimeStr))
                    {
                        result+="至"+endDateStr;
                    }else {
                        result+="至"+endDateStr+" "+endTimeStr;
                    }

                }
            }else{
                result+="至"+nowSuitableFormat(endDate);
            }
        }

        return result;
    }

    public static int getDiffByDay(Date startDate,Date endDate)
    {
        if(startDate==null || endDate==null)
        {
            return -1;
        }
        long intervalMill=endDate.getTime()-startDate.getTime();
        return (int)intervalMill/(24 * 60 * 60 *1000);
    }
}
