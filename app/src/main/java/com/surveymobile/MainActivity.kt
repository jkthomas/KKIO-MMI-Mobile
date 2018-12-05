package com.surveymobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    var mywebview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermissions()
        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        mywebview = findViewById<WebView>(R.id.webview)
        /*mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }*/
        val mywebviewSettings = mywebview
        if (mywebviewSettings is WebView) {
            mywebviewSettings.settings.javaScriptEnabled = true
            mywebviewSettings.settings.domStorageEnabled = true
        }
        mywebview!!.loadUrl("file:///android_asset/index.html")
    }

    private fun setPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            //tv1.text = getString(R.string.textview1_permission_denied)
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET), 1)
        } else {
            //tv1.text = getString(R.string.textview1_permission_granted)
        }
    }
}
