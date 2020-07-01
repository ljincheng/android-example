package cn.booktable.uikit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public static boolean isBlank(String str)
    {
        return str==null|| str.length()==0;
    }

    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }


    public static boolean isEmpty(CharSequence str)
    {
        return str==null|| str.length()==0;
    }

    public static boolean isNotEmpty(CharSequence str)
    {
        return !isEmpty(str);
    }



    public static String moneyStr(CharSequence str)
    {
        if(isEmpty(str))
        {
            return null;
        }

        String reg = "(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?(元|￥|\\$)$";
      //  String str = "某一节目第20140502期";
        Pattern pattern = Pattern.compile (reg);
        Matcher matcher = pattern.matcher (str);
        while (matcher.find ())
        {
           return matcher.group ();
        }
//
//        StringBuffer moneySB=new StringBuffer();
//        boolean hasNum=false;
//        for(int i=0,k=str.length();i<k;i++)
//        {
//             char value=str.charAt(i);
//            if(value >= 0 && value<=9 )
//            {
//                if(hasNum==false)
//                {
//                    hasNum=true;
//                }
//                if(hasNum) {
//                    moneySB.append(value);
//                }
//            }else if(hasNum && value == '.')
//            {
//                moneySB.append(value);
//            }else if(hasNum && value == '元')
//            {
//                return moneySB.toString();
//            }
//        }
        return null;
    }

}
