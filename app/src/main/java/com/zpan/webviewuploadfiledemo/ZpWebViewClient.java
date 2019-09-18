package com.zpan.webviewuploadfiledemo;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ZpWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

}
