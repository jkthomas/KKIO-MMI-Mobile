package com.surveymobile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import com.surveymobile.fragments.StatisticsFragment
import com.surveymobile.fragments.StatisticsFragment.OnFragmentInteractionListener
import com.surveymobile.fragments.SurveyFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    lateinit var navigationBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            //permission is not granted - request permisions
            setPermissions()
        } else {
            //permission granted already - show content
            initializeNavigationBar()
        }
    }

    private fun initializeNavigationBar() {
        navigationView.setOnNavigationItemSelectedListener  {
            when(it.itemId){
                R.id.statistics -> {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, StatisticsFragment())
                    ft.commit()
                    true
                }
                R.id.survey -> {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, SurveyFragment())
                    ft.commit()
                    true
                }
                else -> false
            }
        }
        navigationView.selectedItemId = R.id.statistics
    }

    private fun setPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 1)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            val shutdownAlertBuilder = AlertDialog.Builder(this)
            shutdownAlertBuilder.setTitle("Permissions not provided")
            shutdownAlertBuilder.setMessage("Permission not granted. Shutting down...")
            shutdownAlertBuilder.setPositiveButton("YES"){ _, _ -> }
            val shutdownDialog: AlertDialog = shutdownAlertBuilder.create()
            shutdownDialog.show()

            finish()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented")
    }
}
