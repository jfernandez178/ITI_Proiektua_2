package com.example.javier.ukelelearrospi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ander on 2015/12/14.
 */
public class WebViewFragement extends Fragment {

    private WebView webView;

    public static final String WEB_URL = "WEB_URL";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.web_layout, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getArguments().getString(WEB_URL));
        return v;
    }

}
