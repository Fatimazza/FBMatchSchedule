package io.github.fatimazza.fbmatchschedule.matches

import android.graphics.Color
import android.os.Bundle
import android.support.design.R.id.fixed
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.github.fatimazza.fbmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager

class MatchesFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

                    tabLayout {
                        id = R.id.tab_match
                        lparams(width = matchParent, height = wrapContent)
                        tabMode = fixed
                    }

                    viewPager {
                        id = R.id.viewpager_match
                    }.lparams(width = matchParent, height = wrapContent)
                }
            }
        }
    }
}
