package com.technicus.easy2recharge.ui;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.technicus.easy2recharge.R;

public class AddToWalletTransaction extends ActionBarActivity {
    WebView myWebView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_wallet_transaction);
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setDomStorageEnabled(true);

        intent = getIntent();
        String amount = intent.getStringExtra("amount");
        String uid = intent.getStringExtra("uid");
        myWebView.loadUrl("https://easy2recharge.in/api/addEwallet.php?uid=" + uid + "&amount=" + amount);
        myWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                error.getCertificate();
            }
        });
        // myWebView.loadUrl("https://www.google.co.in");
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // view.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
            Log.d("love me forever", url);
            return false;
        }
    }
}

