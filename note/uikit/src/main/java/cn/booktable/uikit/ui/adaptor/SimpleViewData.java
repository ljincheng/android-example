package cn.booktable.uikit.ui.adaptor;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.booktable.common.util.Md5Utils;

public class SimpleViewData {
    private List<JSONObject> mDataList;

    public SimpleViewData(){
        mDataList=new ArrayList<>();
    }

    public SimpleViewData(List<JSONObject> dataList)
    {
        this.mDataList=dataList;
    }

    public int getItemViewType(int index)
    {
        int viewType=0;
        if(mDataList!=null && mDataList.size()>index)
        {
           JSONObject data=  mDataList.get(index);
            if (data!=null && data.containsKey("type")) {
                viewType=  data.getInteger("type");
            }
        }
        return viewType;
    }

    public JSONObject get(int index)
    {
        if(mDataList!=null  && mDataList.size()>index)
        {
            return mDataList.get(index);
        }
        return null;
    }
    public int size()
    {
        if(mDataList==null)
        {
            return 0;
        }else{
            return mDataList.size();
        }
    }


    public boolean append(JSONObject obj,int index)
    {
        if(obj!=null && obj.containsKey("type"))
        {
            String md5Str= Md5Utils.MD5(obj.toJSONString());
            if(mDataList.size()>index)
            {
                String oldMd5Str=mDataList.get(index).toJSONString();
                if(md5Str.equals(oldMd5Str))
                {
                    return false;
                }else{
                    mDataList.set(index,obj);
                    return true;
                }

            }else{
                mDataList.add(obj);
                return true;
            }
        }
        return false;
    }

    public void clear()
    {
        this.mDataList.clear();
    }

    public void setDataList(List<JSONObject> dataList)
    {
        this.mDataList=dataList;
    }
}
