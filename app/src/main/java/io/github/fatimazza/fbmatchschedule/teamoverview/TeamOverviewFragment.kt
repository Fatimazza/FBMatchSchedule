package io.github.fatimazza.fbmatchschedule.teamoverview

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.nextmatch.NextMatchPresenter
import io.github.fatimazza.fbmatchschedule.teamdetail.TeamDetailPresenter
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamOverviewFragment: Fragment(), TeamOverviewView {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var teamDescription: TextView

    private lateinit var presenter: TeamDetailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return TeamOverviewFragment.TeamOverviewFragmentUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class TeamOverviewFragmentUI: AnkoComponent<TeamOverviewFragment> {
        override fun createView(ui: AnkoContext<TeamOverviewFragment>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL

                    owner.swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light)

                        scrollView {
                            isVerticalScrollBarEnabled = false
                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)
                                    orientation = LinearLayout.VERTICAL
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    padding = dip(10)

                                    owner.teamDescription = textView().lparams {
                                        topMargin = dip(16)
                                        bottomMargin = dip(16)
                                    }
                                }
                                owner.progressBar = progressBar {
                                }.lparams {
                                    centerHorizontally()
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun newInstance(id: String): TeamOverviewFragment {
        val fragment = TeamOverviewFragment()
        val args = Bundle()
        args.putString(arguments?.getString(getString(R.string.intent_id)).toString(), id)
        fragment.setArguments(args)
        return fragment
    }
}