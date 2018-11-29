package io.github.fatimazza.fbmatchschedule.teamoverview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.fatimazza.fbmatchschedule.model.Team
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class TeamOverviewFragment: Fragment(), TeamOverviewView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return TeamOverviewFragment.TeamOverviewFragmentUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class TeamOverviewFragmentUI: AnkoComponent<TeamOverviewFragment> {
        override fun createView(ui: AnkoContext<TeamOverviewFragment>): View {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTeamDetail(data: List<Team>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}