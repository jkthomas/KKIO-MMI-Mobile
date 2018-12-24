package com.surveymobile

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import khttp.get
import android.widget.TextView
import com.surveymobile.entity.survey.AnswerStatisticsEntity
import com.surveymobile.entity.survey.QuestionStatisticEntity
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermissions()
    }

    private fun setStatsView() {
        val statsTextView = findViewById<TextView>(R.id.jsonTextView)
        Thread {
            val jsonResponse: Response = get("")

            val questions: MutableList<QuestionStatisticEntity> = mutableListOf()

            val statisticsData: JSONArray = jsonResponse.jsonArray
            for (i in 0 until statisticsData.length()) {
                var questionEntity: QuestionStatisticEntity? = null
                val statisticSingleData = statisticsData.getJSONObject(i)
                questionEntity = QuestionStatisticEntity(statisticSingleData["questionContent"].toString())
                val answerStatData: JSONObject = statisticSingleData["answerStatistics"] as JSONObject
                for (key in answerStatData.keys()) {
                    questionEntity.answerStatistics.add(AnswerStatisticsEntity(key as String, answerStatData[key] as Int))
                }

                questions.add(questionEntity)
            }


            runOnUiThread {
                statsTextView.text = questions.first().answerStatistics.first().answerContent
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
