package cn.booktable.uikit.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import cn.booktable.uikit.ui.adaptor.SimpleRecyclerAdaptor;
import cn.booktable.uikit.ui.adaptor.SimpleView;
import cn.booktable.uikit.ui.adaptor.SimpleViewData;
import cn.booktable.uikit.ui.attr.ViewAttrHelper;

public class GridView  extends RecyclerView implements SimpleView {
    private int mCols=5;//列数
    private GridLayoutManager mPagerLayoutManager;
    SimpleRecyclerAdaptor mAdaptor;
    public GridView(Context context) {
        this(context,null,0);
    }

    public GridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDataView();
    }

    private void initDataView()
    {
         mPagerLayoutManager = new GridLayoutManager(getContext(), mCols);
        setLayoutManager(mPagerLayoutManager);

    }



    @Override
    public void setViewData(JSONObject data) {
        try {
            LinearLayout.LayoutParams rootLp =ViewAttrHelper.newLinearLayoutParams(ViewAttrHelper.MATCH_WRAP);
            setLayoutParams(rootLp);
            ViewAttrHelper.encodeProperties(getContext(),this,data);
            if (data != null) {
                if (data.containsKey("cols")) {
                    this.mCols = data.getInteger("cols").intValue();
                    mPagerLayoutManager = new GridLayoutManager(getContext(), mCols);
                    setLayoutManager(mPagerLayoutManager);
                }
                if(data.containsKey("data")) {
                    JSONArray items=data.getJSONArray("data");
                    List<JSONObject>datas=items.toJavaList(JSONObject.class);
                    SimpleViewData simpleViewData=new SimpleViewData(datas);
                    mAdaptor = new SimpleRecyclerAdaptor(simpleViewData);
                    setAdapter(mAdaptor);
                    refreshDrawableState();

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
