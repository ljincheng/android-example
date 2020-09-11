package cn.booktable.uikit.ui.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSONObject;

import cn.booktable.uikit.ui.widget.RadiusImageView;
import cn.booktable.uikit.util.DisplayHelper;

public class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    private SimpleView mSimpleView;

    public SimpleViewHolder(@NonNull View itemView,SimpleView simpleView) {
        super(simpleView.getView());
        mSimpleView=simpleView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        simpleView.getView().setLayoutParams(params);
    }

    public void setViewData(JSONObject data)
    {
         mSimpleView.setViewData(data);
    }

    @Override
    public void onClick(View v) {

    }
}