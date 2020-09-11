package cn.booktable.uikit.ui.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import cn.booktable.uikit.ui.adaptor.AdaptorViewManage;
import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.adaptor.SimpleViewData;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;
import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.DrawableHelper;
import cn.booktable.uikit.util.LayoutHelper;

public class GroupListView extends DividerLinearLayout implements SimpleView {

    private int hasDivider=SHOW_DIVIDER_NONE;
    private int mDividerHeight=0;
    private int mDividerPaddingLeft=20;
    private Drawable mDivider;

    private LayoutHelper mLayoutHelper;
    public GroupListView(Context context) {
        this(context,null);
    }

    public GroupListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GroupListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public GroupListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mLayoutHelper=new LayoutHelper(context,this);
        setOrientation(LinearLayout.VERTICAL);

        showDividers(hasDivider);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mLayoutHelper.dispatchRoundBorderDraw(canvas);
    }

    public GroupListView setBorderWidth(int value)
    {
        mLayoutHelper.setBorderWidth(value);
        invalidate();
        return this;
    }

    public GroupListView setBorderColor(@ColorInt int color)
    {
        mLayoutHelper.setBorderColor(color);
        invalidate();
        return this;
    }

    public void setRadius(int radius) {
        mLayoutHelper.setRadius(radius);
    }
    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, shadowElevation, shadowAlpha);
    }

    public void addTo(ViewGroup viewGroup)
    {
        viewGroup.addView(this);
    }


    @Override
    public void setViewData(JSONObject data) {
        LinearLayout.LayoutParams rootLp =ViewAttrHelper.newLinearLayoutParams(ViewAttrHelper.MATCH_WRAP);
        setLayoutParams(rootLp);
        ViewAttrHelper.encodeProperties(getContext(),this,data);
        if(data.containsKey("divider"))
        {
            if(data.getBoolean("divider"))
            {
                setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE|LinearLayout.SHOW_DIVIDER_END);
                int borderColor=Color.GRAY;
                if(data.containsKey("borderColor"))
                {
                    borderColor=Color.parseColor(data.getString("borderColor"));
                }
                setBorderColor(borderColor).setBorderWidth(DisplayHelper.dp2px(getContext(),2));
                setDividerPaddingLeft(0);
            }
        }
        if(data.containsKey("data")) {
            removeAllViews();
            JSONArray items=data.getJSONArray("data");
//            List<JSONObject> datas=items.toJavaList(JSONObject.class);
            for(int i=0,k=items.size();i<k;i++)
            {
                JSONObject item=items.getJSONObject(i);
                if(item.containsKey("type"))
                {
                    Integer type=item.getInteger("type");
                    SimpleView childView= AdaptorViewManage.createViewType(this,type.intValue());
                    if(childView!=null)
                    {
                        childView.setViewData(item);
                        addView(childView.getView());
                    }

                }
            }

        }
    }

    @Override
    public View getView() {
        return this;
    }
}
