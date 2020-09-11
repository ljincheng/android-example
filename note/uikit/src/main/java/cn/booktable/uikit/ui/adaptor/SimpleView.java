package cn.booktable.uikit.ui.adaptor;

import android.view.View;

import com.alibaba.fastjson.JSONObject;

public interface SimpleView {

   void setViewData(JSONObject data);

   View getView();
}
