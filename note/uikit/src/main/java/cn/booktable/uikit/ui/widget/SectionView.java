package cn.booktable.uikit.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import java.math.BigDecimal;

import cn.booktable.uikit.R;
import cn.booktable.uikit.util.LayoutHelper;

public class SectionView extends DividerLinearLayout {

    private int hasDivider = SHOW_DIVIDER_NONE;
    private int mDividerHeight = 0;
    private int mDividerPaddingLeft = 20;
    private Drawable mDivider;

    private LayoutHelper mLayoutHelper;
//    private LinearLayout mLayoutHeader;
    private LinearLayout mLayoutBody;

    private int shortAnimationDuration;
    private Animator currentAnimator;

    public SectionView(Context context) {
        this(context, null);
    }

    public SectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);

    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_section_view, this, true);
        mLayoutHelper = new LayoutHelper(context, this);
        setOrientation(LinearLayout.VERTICAL);
//        mLayoutHeader = findViewById(R.id.layout_header);
        mLayoutBody = findViewById(R.id.layout_body);
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mLayoutHelper.dispatchRoundBorderDraw(canvas);
    }

    public SectionView setBorderWidth(int value) {
        mLayoutHelper.setBorderWidth(value);
        invalidate();
        return this;
    }

    public SectionView setBorderColor(@ColorInt int color) {
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

    public void addTo(ViewGroup viewGroup) {
        viewGroup.addView(this);
    }

    public SectionView setHeaderView(HeaderView headerView)
    {
        if(headerView!=null) {
//            TextView headerRightTextView = new TextView(getContext());
//            headerRightTextView.setText("显示/隐藏");
            headerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentAnimator != null) {
                        currentAnimator.cancel();
                    }
                    int visibilityValue= mLayoutBody.getVisibility();
//                    crossfade(visibilityValue);
//                    zoomView(visibilityValue);
                    translateAnimationView(visibilityValue);
//                    if(visibilityValue==GONE) {
//                        mLayoutBody.setVisibility(VISIBLE);
//                    }else{
//                        mLayoutBody.setVisibility(GONE);
//                    }
                }
            });
//            headerView.setIconType(HeaderView.ACCESSORY_TYPE_CUSTOM).addIconView(headerRightTextView);
//            mLayoutHeader.addView(headerView);
            this.addView(headerView,0);


        }
        return this;
    }


    private void crossfade(int visibility)
    {
        final Rect startBounds = new Rect();
        mLayoutBody.getGlobalVisibleRect(startBounds);
        if(visibility == GONE) {
            mLayoutBody.setAlpha(0f);
            mLayoutBody.setVisibility(View.VISIBLE);
            mLayoutBody.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);
        }else{
            mLayoutBody.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLayoutBody.setVisibility(View.GONE);
                        }
                    });
        }
    }
    private void zoomView(int visibility)
    {
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        AnimatorSet set = new AnimatorSet();
        if(visibility==GONE) {
            mLayoutBody.setAlpha(1f);
            mLayoutBody.setVisibility(View.VISIBLE);
            int startY=mLayoutBody.getHeight();
            mLayoutBody.getLocalVisibleRect(startBounds);
            set.play(ObjectAnimator.ofFloat(mLayoutBody, View.SCALE_Y,
                    0f, 1f)).with(ObjectAnimator.ofFloat(mLayoutBody, View.TRANSLATION_X,
                    0)).with(ObjectAnimator.ofFloat(mLayoutBody, View.TRANSLATION_Y,
                    -startY,0));
            set.setDuration(shortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());

        }else{
            int startY=mLayoutBody.getHeight();
            mLayoutBody.getLocalVisibleRect(startBounds);
            set.play(ObjectAnimator.ofFloat(mLayoutBody, View.SCALE_Y,
                    1f, 0f)).with(ObjectAnimator.ofFloat(mLayoutBody, View.TRANSLATION_X,
                  0)).with(ObjectAnimator.ofFloat(mLayoutBody, View.TRANSLATION_Y,
                    0,-startY));
            set.setDuration(shortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                   // mLayoutBody.setAlpha(1f);
                    mLayoutBody.setVisibility(View.GONE);
                    currentAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                   // mLayoutBody.setAlpha(1f);
                    mLayoutBody.setVisibility(View.GONE);
                    currentAnimator = null;
                }
            });
        }
        set.start();
        currentAnimator = set;
    }


    private void translateAnimationView(int visibility)
    {
        if(visibility==GONE) {
            mLayoutBody.setVisibility(View.VISIBLE);
            mLayoutBody.setAlpha(0.0f);
            mLayoutBody.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setListener(null);
        }else {
            int height=mLayoutBody.getHeight();
            mLayoutBody.animate()
                .translationY(-height)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mLayoutBody.setVisibility(View.GONE);
                    }
                });
        }
    }

    public SectionView setBobyView(View view)
    {
        mLayoutBody.removeAllViews();
        mLayoutBody.addView(view);
        return  this;
    }

    public SectionView addBodyView(View view)
    {
        mLayoutBody.addView(view);
        return this;
    }

    public SectionView removeBodyView()
    {
        mLayoutBody.removeAllViews();
        return this;
    }
}
