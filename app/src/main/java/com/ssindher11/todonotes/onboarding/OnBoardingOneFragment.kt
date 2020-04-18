package com.ssindher11.todonotes.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ssindher11.todonotes.R

class OnBoardingOneFragment : Fragment() {

    private lateinit var nextTV: TextView
    lateinit var onNextClick: OnNextClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setClickListeners()
    }

    private fun bindViews(view: View) {
        nextTV = view.findViewById(R.id.tv_next_one)
    }

    private fun setClickListeners() {
        nextTV.setOnClickListener { onNextClick.onClick() }
    }

    interface OnNextClick {
        fun onClick()
    }
}