package com.ssindher11.todonotes.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingOneFragment()
            1 -> OnBoardingTwoFragment()
            else -> OnBoardingOneFragment()
        }
    }

    override fun getCount(): Int = 2

}