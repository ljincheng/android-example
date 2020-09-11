package cn.booktable.note.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import cn.booktable.mediaplayer.activities.OneVideoActivity;
import cn.booktable.note.R;
import cn.booktable.note.myApplication;
import cn.booktable.note.ui.adapter.FragmentWebAdapter;
import cn.booktable.note.ui.fragment.FragmentWeb;
import cn.booktable.uikit.util.StringHelper;

public class ActivityWeb extends UserActivity {

    private WebView mWebView;
    private String mWebUrl;
    private String mWebTitle;

    @Override
    public int fragmentContainerId() {
        return R.id.fragment_container;
    }

    public static Intent newIntent(Context context, String webPath, String webTitle) {
        Intent intent = new Intent(context, ActivityWeb.class);
        intent.putExtra("webPath", webPath);
        intent.putExtra("webTitle", webTitle);
        return intent;
    }

    public static void intentTo(Context context, String webPath, String webTitle) {
        context.startActivity(newIntent(context, webPath, webTitle));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebUrl = getIntent().getStringExtra("webPath");
        mWebTitle=getIntent().getStringExtra("webTitle");
//        if (getIntent() != null && getIntent().getExtras() != null) {
////            Bundle extras = getIntent().getExtras();
////            mWebUrl =extras.getString("webPath");
////        }
        setContentView(R.layout.activity_web);

        mWebView=findViewById(R.id.webview);


        // 应用栏设置
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        if(StringHelper.isNotBlank(mWebTitle)) {
            ab.setTitle(mWebTitle);
        }

        initWebView();
    }

    private void initWebView()
    {
        WebSettings webSettings=mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        Map header=new HashMap<String,String>();
//        if(myApplication.getToken()!=null)
//        {
//            header.put("token",myApplication.getToken());
//        }
        mWebView.loadUrl(mWebUrl,header);


    }






    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack())
        {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home)//返回键
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if("10.52.10.128".equals(request.getUrl().getHost()))
            {
                return false;
            }
            Intent intent=new Intent(Intent.ACTION_VIEW,request.getUrl());
            startActivity(intent);
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//            super.onReceivedError(view, request, error);

            if (request.isForMainFrame()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    onReceivedError(view,
                            error.getErrorCode(), "地址错误",
                            "");
                }else {
                    super.onReceivedError(view, request, error);
                }
            }
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            // super.onReceivedHttpError(view, request, errorResponse);
        }
    }
}