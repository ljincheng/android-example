package cn.booktable.uikit.ui.attr;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;

import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.widget.GalleryView;
import cn.booktable.uikit.ui.widget.GroupListView;
import cn.booktable.uikit.ui.widget.RadiusImageView;
import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.LayoutHelper;

public class ViewAttrHelper {

    public static int WRAP_WRAP = 0; // Wrap,Wrap 0000
    public static int WRAP_MATCH = 1; // Wrap,Match 0001
    public static int MATCH_WRAP = 2; // Match,Wrap  0010
    public static int MATCH_MATCH = 3; // Match,Match 0011

    /**
     * 掩码第几位是否为1
     * @param pos 第几位
     * @param value 值
     * @return
     */
    private static boolean trueInMask(int pos, int value) {
          int b= (pos & value );
        return b!=0;
    }

    private static int getWith(int size)
    {
        boolean isMatch=trueInMask(2,size);
        return isMatch?ViewGroup.LayoutParams.MATCH_PARENT:ViewGroup.LayoutParams.WRAP_CONTENT;
    }
    private static int getHeight(int size)
    {
        boolean isMatch=trueInMask(1,size);
        return isMatch?ViewGroup.LayoutParams.MATCH_PARENT:ViewGroup.LayoutParams.WRAP_CONTENT;
    }


    public static LinearLayout.LayoutParams newLinearLayoutParams(int size)
    {
        int width=getWith(size);
        int height=getHeight(size);
       return  new LinearLayout.LayoutParams(width,height);
    }

    public static RecyclerView.LayoutParams newRecyclerLayoutParams(int size)
    {
        int width=getWith(size);
        int height=getHeight(size);
       return  new RecyclerView.LayoutParams(width,height);
    }




    public static void encodeProperties(Context context, View view, JSONObject data)
    {
        if (data == null) {
            return ;
        }
        //===========  LayoutParams 设置开始================================
        boolean useLp=false;
        ViewGroup.LayoutParams lp= view.getLayoutParams();
        if(lp==null)
        {
            lp=newLinearLayoutParams(MATCH_WRAP);
        }
        if (data.containsKey("height")) {
            int  height = data.getInteger("height").intValue();
            if(height>=0) {
                lp.height=DisplayHelper.dp2px(context, height);
                useLp=true;
            }
        }

        if(data.containsKey("margin") || data.containsKey("marginH") || data.containsKey("marginV")) {
            int marginL=0,marginT=0,marginR=0,marginB=0;
            if(lp instanceof ViewGroup.MarginLayoutParams) {
                if (data.containsKey("margin")) {
                    int mMargin = data.getInteger("margin").intValue();
                    marginL =DisplayHelper.dp2px(context, mMargin);
                    marginT = marginL;
                    marginR = marginL;
                    marginB = marginL;
                }
                if (data.containsKey("marginH")) {
                    int mMarginH = data.getInteger("marginH").intValue();
                    marginL = DisplayHelper.dp2px(context, mMarginH);;
                    marginR = marginL;
                }
                if (data.containsKey("marginV")) {
                    int mMarginH = data.getInteger("marginV").intValue();
                    marginL =  DisplayHelper.dp2px(context, mMarginH);;
                    marginR = marginL;
                }

                ( (ViewGroup.MarginLayoutParams)lp).setMargins(marginL, marginT, marginR, marginB);
                useLp=true;
            }
        }
        if(useLp)
        {
            view.setLayoutParams(lp);
        }

        //===========  LayoutParams 设置结束================================


        if(data.containsKey("radius"))
        {
            Integer value=data.getInteger("radius");
            if(value!=null) {
                int radiusV=DisplayHelper.dp2px(context,value.intValue());
                if(view instanceof GroupListView)
                {
                    ((GroupListView)view).setRadius(radiusV);
                }else {
                    LayoutHelper mLayoutHelper = new LayoutHelper(context, view);
                    mLayoutHelper.setRadius(radiusV);
                }
            }
        }


        if(data.containsKey("scaleType"))
        {
            if(view instanceof AppCompatImageView)
            {
                String scaleType=data.getString("scaleType");
                ((AppCompatImageView)view).setScaleType(ImageView.ScaleType.valueOf(scaleType));
            }
        }

        if (data.containsKey("img")) {
            String src=data.getString("img");
            if(view instanceof RadiusImageView) {
                ((RadiusImageView)view).setSrc(src);
            }
        }


    }



}
