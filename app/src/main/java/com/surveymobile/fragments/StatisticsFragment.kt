package com.surveymobile.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.surveymobile.R
import com.surveymobile.entity.survey.AnswerStatisticsEntity
import com.surveymobile.entity.survey.QuestionStatisticEntity
import khttp.get
import khttp.responses.Response
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StatisticsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StatisticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var counter: Int = 1
    private var viewOfFragment: View? = null
    private val questions: MutableList<QuestionStatisticEntity> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        createStatistics()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewOfFragment = inflater.inflate(R.layout.fragment_statistics, container, false)
        viewOfFragment?.tempButton?.setOnClickListener {
            if (this.counter == 1) {
                viewOfFragment?.statisticsTextView?.text = getString(R.string.testString)
                this.counter = 0
            } else {
                viewOfFragment?.statisticsTextView?.text = questions.first().answerStatistics.first().toString()
                this.counter = 1
            }
            //questions.first().answerStatistics.first().answerContent
        }

        return viewOfFragment
    }

    fun createStatistics() {
        Thread {
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
        }.start()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatisticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                StatisticsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
