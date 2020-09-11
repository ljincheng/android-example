package cn.booktable.uikit.ui.adaptor;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecyclerAdaptor extends RecyclerView.Adapter<SimpleViewHolder> {


    private SimpleViewData mSimpleViewData;
    private boolean loopPosition=false;//循环

    public SimpleRecyclerAdaptor(@NonNull  SimpleViewData simpleViewData) {
        this.mSimpleViewData = simpleViewData;
        if(this.mSimpleViewData==null)
        {
            this.mSimpleViewData=new SimpleViewData();
        }
    }

    public void setLoopPosition(boolean value)
    {
        this.loopPosition=value;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SimpleView simpleView= AdaptorViewManage.createViewType(parent,viewType);

        return new SimpleViewHolder(parent,simpleView);
    }

    @Override
    public final int getItemViewType(int position) {
        if(this.loopPosition) {
            return mSimpleViewData.getItemViewType(position % mSimpleViewData.size());
        }else{
            return mSimpleViewData.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        if(this.loopPosition)
        {
            holder.setViewData(mSimpleViewData.get(position%mSimpleViewData.size()));
        }else {
            holder.setViewData(mSimpleViewData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(this.loopPosition)
        {
            return Integer.MAX_VALUE;
        }else {
            return mSimpleViewData.size();
        }
    }



}