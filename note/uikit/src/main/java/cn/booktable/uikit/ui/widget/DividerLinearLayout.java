package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.DrawableHelper;

public class DividerLinearLayout extends LinearLayout {

    private int hasDivider=SHOW_DIVIDER_NONE;
    private int mDividerHeight=0;
    private int mDividerPaddingLeft=0;
    private Drawable mDivider;

    public DividerLinearLayout(Context context) {
        this(context,null);
    }

    public DividerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DividerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public DividerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(LinearLayout.VERTICAL);
        init();
    }

    private  void init()
    {
        mDividerHeight=1;
        mDividerPaddingLeft= DisplayHelper.dp2px(getContext(),80);
        mDivider= DrawableHelper.createItemSeparatorBg(Color.GRAY,Color.GRAY,mDividerHeight,false);
        setDividerDrawable(mDivider);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(hasDivider != SHOW_DIVIDER_NONE)
        {
            drawDividersVertical(canvas);
        }
    }

    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == getChildCount()) {
            // Check whether the end divider should draw.
            return (hasDivider & SHOW_DIVIDER_END) != 0;
        }
        boolean allViewsAreGoneBefore = allViewsAreGoneBefore(childIndex);
        if (allViewsAreGoneBefore) {
            // This is the first view that's not gone, check if beginning divider is enabled.
            return (hasDivider & SHOW_DIVIDER_BEGINNING) != 0;
        } else {
            return (hasDivider & SHOW_DIVIDER_MIDDLE) != 0;
        }
    }

    private View getLastNonGoneChild() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                return child;
            }
        }
        return null;
    }
    private boolean allViewsAreGoneBefore(int childIndex) {
        for (int i = childIndex - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                return false;
            }
        }
        return true;
    }

    void drawHorizontalDivider(Canvas canvas, int top,int paddingLeft) {
        mDivider.setBounds(getPaddingLeft() +paddingLeft, top,
                getWidth() - getPaddingRight(), top + mDividerHeight);
        mDivider.draw(canvas);
    }

    void drawDividersVertical(Canvas canvas) {
        final int count = getChildCount();
        int dividerPaddingLeft=0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - lp.topMargin - mDividerHeight;
                    drawHorizontalDivider(canvas, top,dividerPaddingLeft);
                    dividerPaddingLeft= mDividerPaddingLeft;
                }else{
                    dividerPaddingLeft=0;
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getLastNonGoneChild();
            int bottom = 0;
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - mDividerHeight;
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                bottom = child.getBottom() + lp.bottomMargin;
            }
            dividerPaddingLeft=0;
            drawHorizontalDivider(canvas, bottom,dividerPaddingLeft);
        }
    }

    public void showDividers(int value)
    {
        hasDivider=value;
        setShowDividers(hasDivider);
    }

    public void setDividerPaddingLeft(int paddingLeft)
    {
        this.mDividerPaddingLeft=paddingLeft;
    }



}
