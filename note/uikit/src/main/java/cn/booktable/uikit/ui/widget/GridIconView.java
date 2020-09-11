package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;

import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;
import cn.booktable.uikit.util.DisplayHelper;

public class GridIconView extends LinearLayout implements SimpleView,View.OnClickListener {

    private RadiusImageView mImageView;
    private TextView mTextView;
    private JSONObject mViewData;

    public GridIconView(Context context) {
        this(context,null,0);
    }

    public GridIconView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //默认垂直
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams rootLp = ViewAttrHelper.newLinearLayoutParams(ViewAttrHelper.MATCH_WRAP);
        rootLp.height= DisplayHelper.dp2px(context,80);
        setLayoutParams(rootLp);
//        setBackgroundColor(Color.GRAY);
//        rootLp.gravity= Gravity.CENTER_HORIZONTAL;
        setVerticalGravity(Gravity.TOP);
        setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        //setGravity(Gravity.CENTER);


        mImageView = new RadiusImageView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mImageView.setLayoutParams(lp);
        int iconSize=DisplayHelper.dp2px(context,60);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(iconSize,iconSize);
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        mImageView.setBackgroundColor(Color.GREEN);
        addView(mImageView);

        mTextView = new TextView(context);
        mTextView.setLayoutParams(lp);
        mTextView.setGravity(Gravity.CENTER);
        addView(mTextView);
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View event)
    {
        if(mViewData!=null && mViewData.containsKey(WidgetContext.EVENT_KEY)) {
            WidgetContext.onClickEvent(this.getContext(),this,mViewData);
        }
    }


    @Override
    public void setViewData(JSONObject data) {

        ViewAttrHelper.encodeProperties(getContext(),this,data);
        if(data!=null)
        {
            String img=data.getString("img");
            String text=data.getString("text");
            mImageView.setSrc(img);
//            mImageView.setBackgroundColor(Color.GRAY);
            mTextView.setText(text);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayHelper.dp2px(getContext(),56));
//            mImageView.setLayoutParams(lp);
            this.mViewData=data;
        }
    }

    @Override
    public View getView() {
        return this;
    }
}