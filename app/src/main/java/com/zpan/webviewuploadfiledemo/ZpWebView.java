package com.zpan.webviewuploadfiledemo;


import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ZpWebView extends WebView {

    private ZpWebChromeClient webChromeClient;

    public ZpWebView(Context context) {
        super(context);
        initWebView();
    }

    public ZpWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView();
    }

    public ZpWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    private void initWebView() {
        webChromeClient = new ZpWebChromeClient();
        setWebChromeClient(webChromeClient);
        setWebViewClient(new ZpWebViewClient());
        WebSettings webviewSettings = getSettings();
        // 不支持缩放
        webviewSettings.setSupportZoom(false);
        // 自适应屏幕大小
        webviewSettings.setUseWideViewPort(true);
        webviewSettings.setLoadWithOverviewMode(true);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + "cache/";
        webviewSettings.setAppCachePath(cacheDirPath);
        webviewSettings.setAppCacheEnabled(true);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setAllowFileAccess(true);
        webviewSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webviewSettings.setSupportMultipleWindows(true);
    }

    public void setOpenFileChooserCallBack(ZpWebChromeClient.OpenFileChooserCallBack callBack) {
        webChromeClient.setOpenFileChooserCallBack(callBack);
    }

    public void setCreateWindowCallBack(ZpWebChromeClient.CreateWindowCallBack callBack) {
        webChromeClient.setCreateWindowCallBack(callBack);
    }
}
