package com.technicus.easy2recharge.ui;


import android.app.Fragment;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.technicus.easy2recharge.R;

public class AddToWalletTransactionFragment extends Fragment {

    WebView myWebView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_to_wallet_transaction, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        myWebView = (WebView) view.findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setDomStorageEnabled(true);

        String amount = getArguments().getString("amount");
        String uid = getArguments().getString("uid");
        myWebView.loadUrl("https://easy2recharge.in/api/addEwallet.php?uid=" + uid + "&amount=" + amount);
        myWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                error.getCertificate();
            }
        });
    }
}
