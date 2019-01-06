package com.surveymobile.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.surveymobile.R
import com.surveymobile.utilities.parsers.AnswerStatisticsParser
import com.surveymobile.utilities.receivers.AnswerStatisticsReceiver
import com.surveymobile.utilities.receivers.receiversInterface.AnswerStatisticsReceiverInterface
import kotlinx.android.synthetic.main.fragment_statistics.view.*

class StatisticsFragment : Fragment() {
    private var viewOfFragment: View? = null
    private val answerStatisticsReceiver: AnswerStatisticsReceiverInterface = AnswerStatisticsReceiver()
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatistics()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewOfFragment = inflater.inflate(R.layout.fragment_statistics, container, false)
        viewOfFragment?.showStatsButton?.setOnClickListener {
            val statistics: String = AnswerStatisticsParser.parseAnswerStatistics(this.answerStatisticsReceiver.fillStatisticData())
            if (statistics == "") {
                viewOfFragment?.statisticsTextView?.text = getString(R.string.loading)
            } else {
                viewOfFragment?.statisticsTextView?.text = statistics
            }
        }

        viewOfFragment?.hideStatsButton?.setOnClickListener {
            viewOfFragment?.statisticsTextView?.text = ""
        }

        return viewOfFragment
    }

    private fun setStatistics() {
        this.answerStatisticsReceiver.getStatisticData()
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

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticsFragment().apply {}
    }
}
