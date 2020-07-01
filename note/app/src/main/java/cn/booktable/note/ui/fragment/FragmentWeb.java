package cn.booktable.note.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import cn.booktable.note.R;
import cn.booktable.note.myApplication;

public class FragmentWeb extends BaseFragment {

    public static String TAG_NAME="FRAGMENT_WEB";

    private WebView mWebView;
    private String mWebUrl;


    @Override
    public String getTagName() {
        return TAG_NAME;
    }

    public static FragmentWeb newInstance(String webUrl) {
        FragmentWeb f=new FragmentWeb();
        Bundle args = new Bundle();
        args.putString("url", webUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_web, container, false);
        mWebView=view.findViewById(R.id.webview);
        WebSettings webSettings=mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        Map header=new HashMap<String,String>();
        if(myApplication.getToken()!=null)
        {
            header.put("token",myApplication.getToken());
        }
        mWebView.loadUrl(mWebUrl,header);

        return view;
    }


    public boolean onWebViewGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack()
    {
        mWebView.goBack();
    }



    private class MyWebViewClient extends WebViewClient{

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
