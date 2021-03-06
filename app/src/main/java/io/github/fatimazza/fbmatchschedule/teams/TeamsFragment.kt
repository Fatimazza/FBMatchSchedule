package io.github.fatimazza.fbmatchschedule.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Leagues
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.teamdetail.TeamDetailActivity
import io.github.fatimazza.fbmatchschedule.util.SpinnerAdapter
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.isOnline
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.lang.RuntimeException

class TeamsFragment : Fragment(), TeamsView, SearchView.OnQueryTextListener {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    private var leagues: MutableList<Leagues> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter

    private lateinit var leagueName: String

    private var searchItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
        initPresenter()

        getDataListOnSpinner()
        getDataOnSpinnerClicked()
        refreshSwipeRefresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return TeamsUI().createView(
                AnkoContext.create(requireContext(), this, false))
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

    private fun initSpinner(leagues: List<Leagues>) {
        val spinnerAdapter = SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, leagues)
        spinner.adapter = spinnerAdapter
    }

    private fun initAdapter() {
        adapter = TeamsAdapter(teams) { teamItem: Team -> teamItemClicked(teamItem)}
        listTeam.adapter = adapter
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
    }

    private fun getDataListOnSpinner() {
        if (isOnline(requireContext())) presenter.getLeaguesList()
    }

    private fun getDataTeamList(position: Int) {
        leagueName = leagues.get(position)
                .strLeague?.replace(" ","%20").toString()
        if (isOnline(requireContext())) presenter.getTeamList(leagueName)
    }

    private fun getDataOnSpinnerClicked() {
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long) {
                getDataTeamList(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }

        }
    }

    private fun refreshSwipeRefresh() {
        swipeRefresh.onRefresh {
            getDataListOnSpinner()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueList(data: List<Leagues>) {
        leagues.clear()
        leagues.addAll(data)

        initSpinner(data)
        getDataTeamList(spinner.selectedItemPosition)
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun teamItemClicked(teamItem: Team) {
        requireContext().startActivity<TeamDetailActivity>(getString(R.string.intent_id) to teamItem.teamId)
    }

    override fun onResume() {
        super.onResume()
        if (null != searchItem)
            searchItem?.collapseActionView()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.searchview_menu, menu)

        searchItem = menu.findItem(R.id.searchview)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (!query.isNullOrBlank())
            if (isOnline(requireContext())) presenter.searchTeam(query)
        return false
    }

    override fun showError(ex: RuntimeException) {
        Toast.makeText(requireContext(), "Error Loading Data", Toast.LENGTH_LONG).show()
    }
}
