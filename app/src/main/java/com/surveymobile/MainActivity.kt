package com.surveymobile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.util.Log
import khttp.get
import android.widget.TextView
import com.surveymobile.entity.survey.AnswerStatisticsEntity
import com.surveymobile.entity.survey.QuestionStatisticEntity
import com.surveymobile.fragments.StatisticsFragment
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), StatisticsFragment.OnFragmentInteractionListener {

    var statisticsFragment: StatisticsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermissions()
    }

    private fun setStatsView() {
        //val statsTextView = findViewById<TextView>(R.id.jsonTextView)

        statisticsFragment = StatisticsFragment.newInstance("x", "y")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.statisticsFragment, statisticsFragment)
                .addToBackStack(statisticsFragment.toString())
                .commit()
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

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
