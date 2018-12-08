package io.github.fatimazza.fbmatchschedule.matches

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.MODE_FIXED
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.LinearLayout
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.searchmatch.SearchMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager

class MatchesFragment : Fragment() {

    private lateinit var matchTabLayout: TabLayout
    private lateinit var matchViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPagerAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return MatchesFragment.MatchesUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class MatchesUI: AnkoComponent<MatchesFragment> {
        override fun createView(ui: AnkoContext<MatchesFragment>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = Color.WHITE

                    owner.matchTabLayout = tabLayout {
                        id = R.id.tab_match
                        lparams(width = matchParent, height = wrapContent)
                        tabMode = MODE_FIXED
                    }

                    owner.matchViewPager = viewPager {
                        id = R.id.viewpager_match
                    }.lparams(width = matchParent, height = wrapContent)
                }
            }
        }
    }

    private fun initPagerAdapter() {
        matchViewPager.adapter = MatchesPagerAdapter(childFragmentManager, ctx)
        matchTabLayout.setupWithViewPager(matchViewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                ctx.startActivity<SearchMatchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
