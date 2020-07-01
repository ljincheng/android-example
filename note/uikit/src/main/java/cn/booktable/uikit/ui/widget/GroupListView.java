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

import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.DrawableHelper;
import cn.booktable.uikit.util.LayoutHelper;

public class GroupListView extends DividerLinearLayout {

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



}
