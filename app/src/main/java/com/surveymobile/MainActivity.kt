package com.surveymobile

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermissions()
    }

    private fun setPermissions() {
        val tv1 = findViewById<TextView>(R.id.textView1)
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            tv1.text = getString(R.string.textview1_permission_denied)
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET), 1)
        } else {
            tv1.text = getString(R.string.textview1_permission_granted)
        }
    }
}
