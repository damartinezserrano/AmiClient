package com.example.marti.amiclient.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.marti.amiclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TerminosWebView extends Fragment {


    public TerminosWebView() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        TerminosWebView terminosWebView = new TerminosWebView();
        return terminosWebView;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terminos_web_view, container, false);


        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl("https://www.google.com.co");
        webView.setWebViewClient(new WebViewClient());

        return view;
    }

}
