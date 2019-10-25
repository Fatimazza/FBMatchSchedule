package io.github.fatimazza.fbmatchschedule.favorites

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.MODE_FIXED
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.github.fatimazza.fbmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager

class FavoritesFragment : Fragment() {

    private lateinit var favTabLayout: TabLayout
    private lateinit var favViewPager: ViewPager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPagerAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return FavoritesFragment.MatchesUI().createView(
                AnkoContext.create(requireContext(), this, false))
    }

    class MatchesUI: AnkoComponent<FavoritesFragment> {
        override fun createView(ui: AnkoContext<FavoritesFragment>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = Color.WHITE

                    owner.favTabLayout = tabLayout {
                        id = R.id.tab_fav
                        lparams(width = matchParent, height = wrapContent)
                        tabMode = MODE_FIXED
                    }

                    owner.favViewPager = viewPager {
                        id = R.id.viewpager_fav
                    }.lparams(width = matchParent, height = wrapContent)
                }
            }
        }
    }

    private fun initPagerAdapter() {
        favViewPager.adapter = FavoritesPagerAdapter(childFragmentManager, requireContext())
        favTabLayout.setupWithViewPager(favViewPager)
    }
}
