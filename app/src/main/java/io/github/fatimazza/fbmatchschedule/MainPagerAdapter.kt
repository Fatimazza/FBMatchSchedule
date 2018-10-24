package io.github.fatimazza.fbmatchschedule

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val NUMBER_OF_TAB: Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> LastMatchFragment()
            else -> {
                return NextMatchFragment()
            }
        }
    }

    override fun getCount(): Int { return NUMBER_OF_TAB }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> R.string.tab_last_match.toString()
            else -> {
                R.string.tab_next_match.toString()
            }
        }
    }
}