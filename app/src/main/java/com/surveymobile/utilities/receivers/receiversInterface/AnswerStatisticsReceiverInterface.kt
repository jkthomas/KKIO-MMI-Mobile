package com.surveymobile.utilities.receivers.receiversInterface

import com.surveymobile.entity.survey.QuestionStatisticEntity

interface AnswerStatisticsReceiverInterface {
    fun getStatisticData()
    fun fillStatisticData() : MutableList<QuestionStatisticEntity>
}