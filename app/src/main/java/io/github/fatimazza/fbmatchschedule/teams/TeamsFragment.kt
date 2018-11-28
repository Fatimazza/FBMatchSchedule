package io.github.fatimazza.fbmatchschedule.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.ctx

class TeamsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return TeamsUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class TeamsUI: AnkoComponent<TeamsFragment> {
        override fun createView(ui: AnkoContext<TeamsFragment>): View {
            return with(ui) {
                frameLayout {

                }
            }
        }
    }

}
