package com.zpan.webviewuploadfiledemo;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class ZpWebChromeClient extends WebChromeClient {

    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    //For Android 3.0 - 4.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }
    }

    // For Android 4.0 - 5.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android > 5.0
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.showFileChooserCallBack(filePathCallback, fileChooserParams);
        }
        return true;
    }

    public void setOpenFileChooserCallBack(OpenFileChooserCallBack callBack) {
        mOpenFileChooserCallBack = callBack;
    }

    public interface OpenFileChooserCallBack {

        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams);
    }

}
