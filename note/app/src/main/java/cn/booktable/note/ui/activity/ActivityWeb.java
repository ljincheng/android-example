package cn.booktable.note.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import cn.booktable.note.R;
import cn.booktable.note.myApplication;
import cn.booktable.note.ui.adapter.FragmentWebAdapter;
import cn.booktable.note.ui.fragment.FragmentWeb;

public class ActivityWeb extends UserActivity {

    private WebView mWebView;
    private String mWebUrl;

    @Override
    public int fragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            mWebUrl =extras.getString("url");
        }
        setContentView(R.layout.activity_web);

        mWebView=findViewById(R.id.webview);


        // 应用栏设置
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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