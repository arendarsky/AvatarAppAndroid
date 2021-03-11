package com.avatar.ava.presentation.main.fragments.battles.finaists.webview

import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient

class FinalWebViewClient : WebViewClient() {
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        handler?.proceed()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        view?.loadUrl("javascript:AndroidFunction.resize(document.body.scrollHeight)")
        super.onPageFinished(view, url)
    }
}