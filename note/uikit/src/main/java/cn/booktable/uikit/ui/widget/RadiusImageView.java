package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;
import cn.booktable.uikit.util.DisplayHelper;
import cn.booktable.uikit.util.LayoutHelper;

public class RadiusImageView extends AppCompatImageView implements SimpleView,View.OnClickListener {

    private LayoutHelper mLayoutHelper;
    private JSONObject mViewData;

    public RadiusImageView(Context context)
    {
        this(context,null);
    }

    public RadiusImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadiusImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutHelper=new LayoutHelper(context,this);
    }

    public void setSrc(String src, int placeholderImg)
    {
        Glide.with(this.getContext())
                .load(src)
                .placeholder(placeholderImg)//图片加载出来前，显示的图片
                .into(this);

    }

    public void setSrc(String src)
    {
        Glide.with(this.getContext())
                .load(src)
                .into(this);

    }

    public void setRadius(int radius)
    {
        this.mLayoutHelper.setRadius(radius);
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
        try {
                ViewAttrHelper.encodeProperties(getContext(),this,data);
                mViewData=data;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public View getView() {
        return this;
    }
}
