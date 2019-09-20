package com.zpan.webviewuploadfiledemo;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WindowWebFragment extends Fragment {
    private Message message;
    private WebView.WebViewTransport mTransport;

    private ZpWebView  mWv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_window, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWv = view.findViewById(R.id.webview);
        mTransport = (WebView.WebViewTransport) message.obj;
        mTransport.setWebView(mWv);
        message.sendToTarget();
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static WindowWebFragment newInstance() {
        Bundle bundle = new Bundle();
        WindowWebFragment fragment = new WindowWebFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
