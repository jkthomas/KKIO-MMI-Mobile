package com.surveymobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.webkit.WebSettings
import khttp.get
import android.webkit.WebView
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermissions()
    }

    private fun setStatsView() {
        val statsTextView = findViewById<TextView>(R.id.jsonTextView)
        Thread{
            val jsonResponse = get("")

            runOnUiThread{
                statsTextView.text = jsonResponse.text
            }
        }.start()
    }

    private fun setPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            //permission is not granted - request permisions
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET), 1)
            /* IF STILL NOT GRANTED -> longToast("Permission not granted. Shutting down.")
                finish() */
        } else {
            //permission granted already - show content
            setStatsView()
        }
    }
}
