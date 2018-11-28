package io.github.fatimazza.fbmatchschedule.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.support.v4.ctx

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
                }
            }
        }
    }
}
