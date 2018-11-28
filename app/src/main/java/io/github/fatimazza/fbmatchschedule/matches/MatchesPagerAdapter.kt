package io.github.fatimazza.fbmatchschedule.matches

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.lastmatch.LastMatchFragment
import io.github.fatimazza.fbmatchschedule.nextmatch.NextMatchFragment

class MatchesPagerAdapter(fm: FragmentManager, context: Context)
    : FragmentPagerAdapter(fm) {

    private val numberOfTab: Int = 2
    private var ctx: Context = context

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> LastMatchFragment()
            else -> {
                return NextMatchFragment()
            }
        }
    }

    override fun getCount(): Int { return numberOfTab }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> ctx.resources.getString(R.string.tab_last_match)
            else -> {
                ctx.resources.getString(R.string.tab_next_match)
            }
        }
    }
}
