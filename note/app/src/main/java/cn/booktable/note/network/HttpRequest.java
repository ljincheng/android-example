package cn.booktable.note.network;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import cn.booktable.note.myApplication;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.Header;
import cz.msebera.android.httpclient.impl.client.AbstractHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class HttpRequest {

    public static CookieStore COOKIESTORE=null;
    public static final String SERVER_URL="http://10.52.10.128:9501/";//默认APP地址(到接口版本号)
//    public static final String SERVER_URL="http://10.50.22.58:8888:9500/";//默认APP地址(到接口版本号)
    public static final String SERVER_UUID="20200611175900";//默认APP地址(到接口版本号)

    static class ApiAsyncHttpClient extends AsyncHttpClient {

        @Override
        protected AsyncHttpRequest newAsyncHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
            return new CheckNetAsyncHttpRequest(client, httpContext, uriRequest, responseHandler);
        }
    }

    static class CheckNetAsyncHttpRequest extends AsyncHttpRequest {
        public CheckNetAsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, ResponseHandlerInterface responseHandler) {
            super(client, context, request, responseHandler);
        }

        @Override
        public void run() {
            // 如果网络本身有问题则直接取消
            if (!myApplication.hasInternet()) {
                new Thread() {
                    @Override
                    public void run() {
                        // 延迟一秒
                        try {
                            SystemClock.sleep(1000);
                            cancel(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            super.run();
        }
    }

    //    private static HttpRequestManager CLIENT;
    private static AsyncHttpClient CLIENT;

    private HttpRequest() {
    }

    /**
     * 初始化网络请求，包括Cookie的初始化
     *
     */
    public static void init() {
        AsyncHttpClient client = new ApiAsyncHttpClient();
        client.setConnectTimeout(2 * 1000);
        client.setResponseTimeout(30 * 1000);
        //client.setCookieStore(new PersistentCookieStore(context));
        // Set
        HttpRequest.setHttpClient(client);
        // Set Cookie
        // setCookieHeader(AccountHelper.getCookie());
        COOKIESTORE=new PersistentCookieStore(myApplication.context());
        // COOKIESTORE.addCookie();
        client.setCookieStore(COOKIESTORE);
    }

    public static void setHttpClient(AsyncHttpClient c) {
        c.addHeader("Accept-Language", Locale.getDefault().getLanguage());
//        c.addHeader("Host", HOST);
        c.addHeader("Connection", "Keep-Alive");


//        c.addHeader("Accept", "image/webp");
        //noinspection deprecation
        c.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        c.setUserAgent(getUserAgent());
        CLIENT = c;

        initSSL(CLIENT);

    }



    public static void rebuildUserHeader()
    {
        CLIENT.addHeader("uuid", SERVER_UUID);
        String token=myApplication.getToken();
        if(token!=null) {
            CLIENT.addHeader("token", myApplication.getToken());
        }

    }

    public static void setCookieHeader(String cookie) {
        if (cookie!=null && !cookie.isEmpty()) {
            CLIENT.addHeader("Cookie", cookie);
        }

    }


    public static void cleanCookie() {
        // first clear store
        // new PersistentCookieStore(AppContext.getInstance()).clear();
        // clear header
        AsyncHttpClient client = CLIENT;
        if (client != null) {
            HttpContext httpContext = client.getHttpContext();
            CookieStore cookies = (CookieStore) httpContext
                    .getAttribute(HttpClientContext.COOKIE_STORE);
            // 清理Async本地存储
            if (cookies != null) {
                cookies.clear();
            }
            // 清理当前正在使用的Cookie
            client.removeHeader("Cookie");
        }
    }

    private static void initSSL(AsyncHttpClient client) {
        try {
            /// We initialize a default Keystore
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // We load the KeyStore
            trustStore.load(null, null);
            // We initialize a new SSLSocketFacrory
            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
            // We set that all host names are allowed in the socket factory
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            // We set the SSL Factory
            client.setSSLSocketFactory(socketFactory);
            // We initialize a GET http request
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUserAgent() {

//        String vCode = BaseApplication.getVersionName();
//        String version = Build.VERSION.RELEASE; // "1.0" or "3.4b5"
//        String osVer = version.length() > 0 ? version : "1.0";
//
////        String model = Build.MODEL;
//
////        if (id.length() > 0) {
////            model += " Build/" + id;
////        }

//        String ua = String.format("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36\n", Build.VERSION.SDK_INT, QMUIPackageHelper.getAppVersion(BaseApplication.context()), android.os.Build.BRAND,  Build.ID,getAppId());
        String ua="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36";
        return ua;
    }

    /**
     * 获取完整地址
     * @param partUrl
     * @return
     */
    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!partUrl.startsWith("http:") && !partUrl.startsWith("https:")) {
            url = SERVER_URL+partUrl;
        }
        return url;
    }

    public static void post(String partUrl, ResponseHandlerInterface handler) {
        rebuildUserHeader();
        CLIENT.post(getAbsoluteApiUrl(partUrl), handler);
    }


    public static void post(String partUrl, String raw,ResponseHandlerInterface handler) {
        rebuildUserHeader();
        try {
            StringEntity entity = new StringEntity(raw);
            CLIENT.post(myApplication.context(), getAbsoluteApiUrl(partUrl), entity, "application/json", handler);
        }catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void post(String partUrl, Map<String, String> paramMap,
                            ResponseHandlerInterface handler) {
        rebuildUserHeader();
        RequestParams params =null;
        if(paramMap==null)
        {
            params=new RequestParams();
        }else {
            params = new RequestParams(paramMap);
        }
        CLIENT.post(getAbsoluteApiUrl(partUrl), params, handler);

    }

    public static void get(String partUrl, ResponseHandlerInterface handler) {
        rebuildUserHeader();
        CLIENT.get(getAbsoluteApiUrl(partUrl), handler);
    }

    public static void get(String partUrl, String raw,ResponseHandlerInterface handler) {
        rebuildUserHeader();
        try {
            StringEntity entity = new StringEntity(raw);
            CLIENT.get(myApplication.context(), getAbsoluteApiUrl(partUrl), entity, "application/json", handler);
        }catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void get(String partUrl, Map<String, String> paramMap,
                           ResponseHandlerInterface handler) {
        rebuildUserHeader();
        RequestParams params = new RequestParams(paramMap);
        CLIENT.get(getAbsoluteApiUrl(partUrl), params, handler);
    }

    public static boolean statusCodeOk(int statusCode)
    {
        return statusCode==200;
    }
}
