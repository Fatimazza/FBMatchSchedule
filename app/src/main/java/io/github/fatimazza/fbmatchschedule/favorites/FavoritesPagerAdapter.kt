package io.github.fatimazza.fbmatchschedule.favorites

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.favoritematch.FavoriteMatchFragment
import io.github.fatimazza.footballclub.favoritesfragment.FavoriteTeamFragment

class FavoritesPagerAdapter(fm: FragmentManager, context: Context)
    : FragmentPagerAdapter(fm) {

    private val numberOfTab: Int = 2
    private var ctx: Context = context

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> FavoriteMatchFragment()
            else -> {
                return FavoriteTeamFragment()
            }
        }
    }

    override fun getCount(): Int { return numberOfTab }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> ctx.resources.getString(R.string.fav_matches)
            else -> {
                ctx.resources.getString(R.string.fav_teams)
            }
        }
    }
}
