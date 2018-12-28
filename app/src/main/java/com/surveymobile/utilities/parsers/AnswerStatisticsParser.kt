package com.surveymobile.utilities.parsers

import com.surveymobile.entity.survey.QuestionStatisticEntity
import java.lang.Exception

class AnswerStatisticsParser {
    companion object {
        fun parseAnswerStatistics(questions: MutableList<QuestionStatisticEntity>) : String {
            var questionCounter = 1
            var statisticsString = ""
            try {
                questions.forEach{ question ->
                    statisticsString += questionCounter
                    statisticsString += ") "
                    statisticsString += question.questionContent
                    statisticsString += "\n"
                    question.answerStatistics.forEach{  answer ->
                        statisticsString += answer.answerContent
                        statisticsString += ": "
                        statisticsString += answer.answerCount
                        statisticsString += "\n"
                    }
                    questionCounter += 1
                }
            } catch (e: Exception) {

            } finally {
                return statisticsString
            }
        }
    }
}