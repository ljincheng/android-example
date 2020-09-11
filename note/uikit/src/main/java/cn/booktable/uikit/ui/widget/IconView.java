package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import cn.booktable.mediaplayer.activities.OneVideoActivity;
import cn.booktable.uikit.R;
import cn.booktable.uikit.ui.activities.WidgetEvents;
import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;

public class IconView extends FrameLayout implements SimpleView,View.OnClickListener {

    private RadiusImageView mImageView;
    private TextView mTextView;
    private JSONObject mViewData;


    public IconView(Context context) {
        this(context,null,0);
    }

    public IconView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_icon_view, this, true);
        mImageView=findViewById(R.id.mImageView);
        mTextView=findViewById(R.id.mTextView);

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
            if(data.containsKey("scaleType"))
            {
                    String scaleType=data.getString("scaleType");
                    mImageView.setScaleType(ImageView.ScaleType.valueOf(scaleType));
            }
            String img=data.getString("img");
            String text=data.getString("text");
            mImageView.setSrc(img);
//            mImageView.setBackgroundColor(Color.GRAY);
            mTextView.setText(text);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayHelper.dp2px(getContext(),56));
//            mImageView.setLayoutParams(lp);
            mViewData=data;
        }
    }

    @Override
    public View getView() {
        return this;
    }
}
