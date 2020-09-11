package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import cn.booktable.uikit.ui.activities.WidgetEvents;

public class WidgetContext {

    public static String EVENT_KEY="event";
    public static boolean onClickEvent(Context context, View view, JSONObject viewData){
        if(context instanceof WidgetEvents) {
            WidgetEvents mWidgetEvents=(WidgetEvents)context;
            mWidgetEvents.OnClickEvent(view,viewData);
            return true;
        }
        return false;
    }
}
