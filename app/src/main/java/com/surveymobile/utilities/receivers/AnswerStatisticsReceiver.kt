package com.surveymobile.utilities.receivers

import android.util.Log
import com.surveymobile.entity.survey.AnswerStatisticsEntity
import com.surveymobile.entity.survey.QuestionStatisticEntity
import com.surveymobile.utilities.receivers.receiversInterface.AnswerStatisticsReceiverInterface
import khttp.get
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class AnswerStatisticsReceiver : AnswerStatisticsReceiverInterface {
    private val questions: MutableList<QuestionStatisticEntity> = mutableListOf()

    override fun getStatisticData(){
        Thread {
            try {
                val jsonResponse: Response = get("")
                val statisticsData: JSONArray = jsonResponse.jsonArray

                for (i in 0 until statisticsData.length()) {
                    var questionEntity: QuestionStatisticEntity?
                    val statisticSingleData = statisticsData.getJSONObject(i)
                    questionEntity = QuestionStatisticEntity(statisticSingleData["questionContent"].toString())
                    val answerStatData: JSONObject = statisticSingleData["answerStatistics"] as JSONObject
                    for (key in answerStatData.keys()) {
                        questionEntity.answerStatistics.add(AnswerStatisticsEntity(key as String, answerStatData[key] as Int))
                    }

                    questions.add(questionEntity)
                }
            } catch (e: Exception){
                Log.d(this::class.toString(), e.message)
            }
        }.start()
    }

    override fun fillStatisticData() : MutableList<QuestionStatisticEntity> {
        return this.questions
    }
}