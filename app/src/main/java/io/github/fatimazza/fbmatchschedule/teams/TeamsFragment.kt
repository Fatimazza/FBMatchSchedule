package io.github.fatimazza.fbmatchschedule.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment : Fragment(), TeamsView {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initSpinner()
        initAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return TeamsUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class TeamsUI: AnkoComponent<TeamsFragment> {
        override fun createView(ui: AnkoContext<TeamsFragment>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    owner.spinner = spinner {
                        id = R.id.spinner
                    }
                    owner.swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light)

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)

                            owner.listTeam = recyclerView {
                                id = R.id.list_team
                                lparams(width = matchParent, height = wrapContent)
                                layoutManager = LinearLayoutManager(ctx)
                            }

                            owner.progressBar = progressBar {
                            }.lparams{
                                centerHorizontally()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initSpinner() {
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
    }

    private fun initAdapter() {
        adapter = TeamsAdapter(teams) { teamItem: Team -> teamItemClicked(teamItem)}
        listTeam.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {

    }

    private fun teamItemClicked(teamItem: Team) {

    }

}
