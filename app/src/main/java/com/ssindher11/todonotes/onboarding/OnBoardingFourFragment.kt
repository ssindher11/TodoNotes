package com.ssindher11.todonotes.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ssindher11.todonotes.R

class OnBoardingFourFragment : Fragment() {

    private lateinit var doneTV: TextView
    private lateinit var backTV: TextView

    lateinit var onButtonClick: OnButtonClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onButtonClick = context as OnButtonClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_boarding_four, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setClickListeners()
    }

    private fun bindViews(view: View) {
        doneTV = view.findViewById(R.id.tv_done_four)
        backTV = view.findViewById(R.id.tv_back_four)
    }

    private fun setClickListeners() {
        doneTV.setOnClickListener { onButtonClick.onDone() }
        backTV.setOnClickListener { onButtonClick.onFourBack() }
    }

    interface OnButtonClick {
        fun onDone()
        fun onFourBack()
    }
}