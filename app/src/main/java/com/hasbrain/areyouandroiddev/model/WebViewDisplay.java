package com.hasbrain.areyouandroiddev.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hasbrain.areyouandroiddev.R;

/**
 * Created by sinhhx on 8/24/16.
 */
public class WebViewDisplay extends AppCompatActivity {
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        extras = getIntent().getExtras();
        String url = extras.getString("link");
        WebView webview = (WebView)findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
    }
}