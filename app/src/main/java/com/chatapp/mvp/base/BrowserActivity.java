package com.chatapp.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chatapp.R;
import com.chatapp.utils.Log;
import com.chatapp.utils.WidgetUtil;


public class BrowserActivity extends BaseActivity {
    public static final String TITLE = "title";

    public static final String ARG_LOAD_WEB_URL = "loadWebUrl";
    private WebView wvContent;
    private View mLoadingView;
    private int mShortAnimationDuration;

    private String url;
    private View layoutError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent intent = getIntent();

        String title = intent.getStringExtra(TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }

        url = intent.getStringExtra(ARG_LOAD_WEB_URL);
        Log.d("url = " + url);

        wvContent = (WebView) findViewById(R.id.wv_content);
        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvContent.getSettings().setLoadWithOverviewMode(true);
        wvContent.getSettings().setUseWideViewPort(true);
        wvContent.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        wvContent.setInitialScale(1);

        layoutError = findViewById(R.id.layout_connection_lost);
        mLoadingView = findViewById(R.id.layout_loading);
        layoutError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPage();
            }
        });

        // Initially hide the content view.
//        tvContent.setVisibility(View.GONE);
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        loadPage();
    }

    private void loadPage() {
        mLoadingView.setVisibility(View.VISIBLE);
        wvContent.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.loadUrl(url);
        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WidgetUtil.switchView(wvContent, mLoadingView, mShortAnimationDuration);
                layoutError.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                wvContent.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                layoutError.setVisibility(View.VISIBLE);
            }
        });
    }

}
