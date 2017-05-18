package com.xilada.xldutils.activitys;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xilada.xldutils.R;
import com.xilada.xldutils.netstatus.NetUtils;

/**
 * 网页加载
 */
public class WebViewActivity extends TitleBarActivity {

    private WebView webView;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_web_view;
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        webView = bind(R.id.webView);
        String url = getIntent().getStringExtra("url");
        final String t = getIntent().getStringExtra("title");
        if (url==null){
            url = "";
        }
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (isUseDefaultTitle()){
                    setTitle(title);
                }else {
                    setTitle(t);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                bind(R.id.ll_loading).setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                bind(R.id.ll_loading).setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    webView.loadUrl(url);
                }
                return true;
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
    private boolean isUseDefaultTitle(){
        return false;
    }
}
