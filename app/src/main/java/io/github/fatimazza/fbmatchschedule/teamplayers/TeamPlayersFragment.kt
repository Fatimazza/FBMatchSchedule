package io.github.fatimazza.fbmatchschedule.teamplayers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Players
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.teamplayersdetail.TeamPlayerDetailActivity
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamPlayersFragment: Fragment(), TeamPlayersView {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private  lateinit var listPlayer: RecyclerView

    private var players: MutableList<Players> = mutableListOf()
    private lateinit var presenter: TeamPlayersPresenter
    private lateinit var adapter: TeamsPlayersAdapter

    private lateinit var idTeam: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return TeamPlayersFragment.TeamOverviewFragmentUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class TeamOverviewFragmentUI: AnkoComponent<TeamPlayersFragment> {
        override fun createView(ui: AnkoContext<TeamPlayersFragment>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL

                    owner.swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light)

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)

                            owner.listPlayer = recyclerView {
                                id = R.id.list_players
                                lparams(width = matchParent, height = wrapContent)
                                layoutManager = LinearLayoutManager(ctx)
                            }

                            owner.progressBar = progressBar { }.lparams {
                                centerHorizontally()
                            }
                        }
                    }

                }
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initArguments()
        initAdapter()
        initPresenter()
        getListPlayers()
        refreshSwipeRefresh()
    }

    private fun initArguments() {
        idTeam= getArguments()?.getString(getString(R.string.intent_id)).toString();
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPlayersPresenter(this, request, gson)
    }

    private fun initAdapter() {
        adapter = TeamsPlayersAdapter(players) { teamItem: Players -> playerItemClicked(teamItem)}
        listPlayer.adapter = adapter
    }

    private fun getListPlayers() {
        presenter.getPlayersList(idTeam)
    }

    private fun refreshSwipeRefresh() {
        swipeRefresh.onRefresh {
            getListPlayers()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<Players>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }

    fun newInstance(id: String): TeamPlayersFragment {
        val fragment = TeamPlayersFragment()
        val args = Bundle()
        args.putString("id", id)
        fragment.setArguments(args)
        return fragment
    }

    private fun playerItemClicked(playerItem: Players) {
        ctx.startActivity<TeamPlayerDetailActivity>(getString(R.string.intent_id) to playerItem.idPlayer)
    }
}