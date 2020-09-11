package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import cn.booktable.uikit.gallery.GalleryViewBase;
import cn.booktable.uikit.gallery.GalleryViewRecyclerAdapter;
import cn.booktable.uikit.ui.adaptor.SimpleRecyclerAdaptor;
import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.adaptor.SimpleViewData;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;

/**
 * 图片轮播
 */
public class GalleryView extends GalleryViewBase<LinearLayoutManager, SimpleRecyclerAdaptor> implements SimpleView {

    private SimpleRecyclerAdaptor mAdapter;

    public GalleryView(Context context) {
        super(context);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBannerScrolled(RecyclerView recyclerView, int dx, int dy) {
        //解决连续滑动时指示器不更新的问题
        if (bannerSize < 2) return;
        int firstReal = mLayoutManager.findFirstVisibleItemPosition();
        View viewFirst = mLayoutManager.findViewByPosition(firstReal);
        float width = getWidth();
        if (width != 0 && viewFirst != null) {
            float right = viewFirst.getRight();
            float ratio = right / width;
            if (ratio > 0.8) {
                if (currentIndex != firstReal) {
                    currentIndex = firstReal;
                    refreshIndicator();
                }
            } else if (ratio < 0.2) {
                if (currentIndex != firstReal + 1) {
                    currentIndex = firstReal + 1;
                    refreshIndicator();
                }
            }
        }
    }

    @Override
    protected void onBannerScrollStateChanged(RecyclerView recyclerView, int newState) {
        int first = mLayoutManager.findFirstVisibleItemPosition();
        int last = mLayoutManager.findLastVisibleItemPosition();
        if (currentIndex != first && first == last) {
            currentIndex = first;
            refreshIndicator();
        }
    }

    @Override
    protected LinearLayoutManager getLayoutManager(Context context, int orientation) {
        return new LinearLayoutManager(context, orientation, false);
    }

    @Override
    protected SimpleRecyclerAdaptor getAdapter(Context context, SimpleViewData viewData, OnBannerItemClickListener onBannerItemClickListener) {
        mAdapter= new SimpleRecyclerAdaptor( viewData);
        mAdapter.setLoopPosition(true);
        return mAdapter;
    }


    @Override
    public void setViewData(JSONObject data) {
        try {
            ViewAttrHelper.encodeProperties(getContext(),this,data);
            if (data != null) {
               //setBackgroundColor(Color.GRAY);
                if(data.containsKey("data")) {
                    JSONArray items=data.getJSONArray("data");
                    List<JSONObject>datas=items.toJavaList(JSONObject.class);
                    SimpleViewData simpleViewData=new SimpleViewData(datas);
                    initBannerImageView(simpleViewData,null);
                }

            }
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
