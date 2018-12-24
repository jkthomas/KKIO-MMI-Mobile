package com.surveymobile.entity.survey

data class QuestionStatisticEntity(val questionContent: String) {
    val answerStatistics: MutableList<AnswerStatisticsEntity> = mutableListOf()
}