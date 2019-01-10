package com.surveymobile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.surveymobile.fragments.StatisticsFragment
import com.surveymobile.fragments.StatisticsFragment.OnFragmentInteractionListener
import com.surveymobile.fragments.SurveyFragment


class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    var statisticsFragment: StatisticsFragment? = null
    var surveyFragment: SurveyFragment? = null

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

            //setStatsFragment()
            setSurveyFragment()
        }
    }

    private fun setPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 1)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            val shutdownAlertBuilder = AlertDialog.Builder(this)
            shutdownAlertBuilder.setTitle("Permissions not provided")
            shutdownAlertBuilder.setMessage("Permission not granted. Shutting down...")
            shutdownAlertBuilder.setPositiveButton("YES"){ dialog, which -> }
            val shutdownDialog: AlertDialog = shutdownAlertBuilder.create()
            shutdownDialog.show()

            finish()
        }
    }

    private fun setStatsFragment() {
        statisticsFragment = StatisticsFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.statisticsFragment, statisticsFragment)
                .addToBackStack(statisticsFragment.toString())
                .commit()
    }

    private fun setSurveyFragment(){
        surveyFragment = SurveyFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.surveyFragment, surveyFragment)
                .addToBackStack(surveyFragment.toString())
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented")
    }
}
