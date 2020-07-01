package cn.booktable.note.network;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class HttpJsonResult {

    private String code;
    private String msg;
    private JSONObject data;
    private String source;

    public static HttpJsonResult getInstance(int statusCode, Header[] headers, byte[] responseBody)
    {
        HttpJsonResult obj=new HttpJsonResult();
        try {
            if(responseBody!=null && responseBody.length>0) {

                String body = new String(responseBody, "UTF-8");
                obj.setSource(body);
                System.out.println("请求返回：" + body);
                if(statusCode==200) {
                    JSONObject jsonObject = new JSONObject(body);
                    obj.setCode(jsonObject.getString("code"));
                    if(jsonObject.has("msg") && jsonObject.get("msg")!=null) {
                        obj.setMsg(jsonObject.getString("msg"));
                    }
                    if(jsonObject.has("data") &&  jsonObject.get("data")!=null) {
                        Object data=jsonObject.get("data");

                        if(!"null".equals(data.toString())) {
                            obj.setData(jsonObject.getJSONObject("data"));
                        }
                    }
                }
            }
        }catch (JSONException e)
        {
            HttpException exception=new HttpException("返回数据失败");
            exception.setStatusCode(statusCode);
            throw  exception;
        }catch (UnsupportedEncodingException ex)
        {
            HttpException exception=new HttpException("返回数据失败");
            exception.setStatusCode(statusCode);
            throw  exception;
        }
        return obj;
    }

    public static HttpJsonResult  getInstance(String source)
    {
        HttpJsonResult obj=new HttpJsonResult();
        try{
            obj.setSource(source);
            JSONObject jsonObject=new JSONObject(source);
            obj.setCode(jsonObject.getString("code"));
            obj.setMsg(jsonObject.getString("msg"));
            obj.setData(jsonObject.getJSONObject("data"));
        }catch (Exception ex)
        {
            HttpException exception=new HttpException(ex);
            throw  exception;
        }
        return obj;
    }

    public boolean okResult()
    {
        return (code!=null && HttpConstants.CODE_SUCCESS.equals(code));
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void println()
    {
        System.out.println("请求返回："+getMsg()==null?getSource():getMsg());
    }
}
