package cn.booktable.note.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cn.booktable.note.network.HttpRequest;
import cn.booktable.note.ui.activity.LoginActivity;
import cz.msebera.android.httpclient.Header;

public class WidgetModel extends ViewModel {

    private MutableLiveData<List<WidgetDo>> widgetLiveData= new MutableLiveData<>();


    public LiveData<List<WidgetDo>> getWidgets()
    {
        return widgetLiveData;
    }

    public void loadFromDatabase()
    {

//        String data="[{\"type\":4,\"height\":150,\"margin\":16,\"radius\":20,\"data\":[{\"type\":3,\"text\":\"快速删除、语音输入\",\"img\":\"https://p3.pstatp.com/list/dfic-imagehandler/e8fe84a1-71cd-4b82-8595-13ce704cfb60\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"短信2\",\"scaleType\":\"FIT_XY\",\"img\":\"https://p3.pstatp.com/list/190x124/pgc-image/6f60a63be7f94218955301177dc82069\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\",\"scaleType\":\"FIT_XY\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\",\"scaleType\":\"FIT_XY\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://p6-tt.byteimg.com/large/dfic-imagehandler/bc01c08d-27e6-4e6e-a8d1-6f3258d4c92a?from=pc\",\"scaleType\":\"FIT_XY\"},{\"type\":3,\"text\":\"短信2\",\"scaleType\":\"FIT_XY\",\"img\":\"https://p6-tt.byteimg.com/large/dfic-imagehandler/bc01c08d-27e6-4e6e-a8d1-6f3258d4c92a?from=pc\",\"scaleType\":\"FIT_XY\"}]},{\"type\":3,\"height\":80,\"marginH\":16,\"radius\":20,\"scaleType\":\"FIT_XY\",\"img\":\"https://imgm.gmw.cn/attachement/jpg/site215/20200813/4477278567318073720.jpg\"},{\"type\":2,\"cols\":4,\"data\":[{\"type\":1,\"text\":\"快速删除、语音输入\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信2\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"快速删除、语音输入\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信2\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"5+环境下使用语音\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"},{\"type\":1,\"text\":\"短信4\",\"img\":\"https://i.stack.imgur.com/XeKat.png\"}]}]";
//        List<JSONObject> jsonObj= JSONArray.parseArray(data,JSONObject.class);
//        List<WidgetDo> widgetDoList=new ArrayList<>();
//        for(int i=0,k=jsonObj.size();i<k;i++)
//        {
//            JSONObject jsdata=jsonObj.get(i);
//            WidgetDo widgetDo=new WidgetDo();
//            widgetDo.setId(i);
//            widgetDo.setSection(1);
//            widgetDo.setData(com.alibaba.fastjson.JSONObject.toJSONString(jsdata));
//            widgetDoList.add(widgetDo);
//
//        }
//        this.widgetLiveData.postValue(widgetDoList);

        loadFromNet();
    }

    private void loadFromNet()
    {
        HttpRequest.get("http://dev.booktable.cn/app/test/testdata.js", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if(responseBody!=null && responseBody.length>0) {

                        String body = new String(responseBody, "UTF-8");

                        System.out.println("请求返回：" + body);
                        if(statusCode==200) {
                          JSONObject jsonObject = JSONObject.parseObject(body);
                          Integer code=jsonObject.getInteger("code");
                          if(code!=null && code==1)
                          {
                              JSONArray datalist= jsonObject.getJSONArray("data");
                              if(datalist!=null && datalist.size()>0)
                              {

                                  List<WidgetDo> widgetDoList=new ArrayList<>();
                                  for(int i=0,k=datalist.size();i<k;i++)
                                  {
                                      JSONObject jsdata=datalist.getJSONObject(i);
                                      WidgetDo widgetDo=new WidgetDo();
                                      widgetDo.setId(i);
                                      widgetDo.setSection(1);
                                      widgetDo.setData(com.alibaba.fastjson.JSONObject.toJSONString(jsdata));
                                      widgetDoList.add(widgetDo);
                                  }
                                  widgetLiveData.postValue(widgetDoList);
                              }

                          }

                        }
                    }

                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {

                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

        });
    }

}
