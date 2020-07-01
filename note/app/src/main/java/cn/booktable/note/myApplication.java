package cn.booktable.note;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.booktable.note.network.HttpRequest;

public class myApplication extends Application {
    private static final String KEY_MYSHAREDPREFERENCES="noteApp.pref";
    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context=getApplicationContext();
        //网络请求实例
        HttpRequest.init();
    }


    public static synchronized Context context() {
        return  _context;
    }

    public static boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }


    public static SharedPreferences getPreferences() {
        return context().getSharedPreferences(KEY_MYSHAREDPREFERENCES, Context.MODE_PRIVATE);
    }


    public static void setAppMap(String key, Integer value) {
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setAppMap(String key, String value) {
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static void setAppMapStr(String key, String value) {
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setAppMap(String key, float value) {
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static String getAppMap(String key,String defValue)
    {
        return getPreferences().getString(key,defValue);
    }

    public static String getAppMapStr(String key)
    {
        return getPreferences().getString(key,null);
    }

    public static int getAppMap(String key,int defValue)
    {
        return getPreferences().getInt(key,defValue);
    }

    public static float getAppMap(String key,float defValue)
    {
        return getPreferences().getFloat(key,defValue);
    }


    public static void setToken(String token)
    {
        setAppMap("token",token);
    }

    public static String getToken()
    {
        return getAppMapStr("token");
    }
}
