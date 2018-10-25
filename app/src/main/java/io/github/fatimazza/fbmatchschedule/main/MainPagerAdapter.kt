package io.github.fatimazza.fbmatchschedule.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.fatimazza.fbmatchschedule.LastMatchFragment
import io.github.fatimazza.fbmatchschedule.NextMatchFragment
import io.github.fatimazza.fbmatchschedule.R

class MainPagerAdapter(fm: FragmentManager, context: Context)
    : FragmentPagerAdapter(fm) {

    private val NUMBER_OF_TAB: Int = 2
    private var ctx: Context = context

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
            0 -> ctx.resources.getString(R.string.tab_last_match)
            else -> {
                ctx.resources.getString(R.string.tab_next_match)
            }
        }
    }
}