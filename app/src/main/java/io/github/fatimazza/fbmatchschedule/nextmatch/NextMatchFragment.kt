package io.github.fatimazza.fbmatchschedule.nextmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.main.MatchView
import io.github.fatimazza.fbmatchschedule.main.MainMatchAdapter
import io.github.fatimazza.fbmatchschedule.main.MatchPresenter
import io.github.fatimazza.fbmatchschedule.matchdetail.MatchDetailActivity
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Leagues
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.util.SpinnerAdapter
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.lang.RuntimeException

class NextMatchFragment: Fragment(), MatchView {

    private var leagues: MutableList<Leagues> = mutableListOf()
    private var events: MutableList<Event> = mutableListOf()

    private lateinit var adapter: MainMatchAdapter
    private lateinit var presenter: MatchPresenter

    private lateinit var spinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listNextEvent: RecyclerView

    private lateinit var idLeague: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return NextMatchFragmentUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class NextMatchFragmentUI: AnkoComponent<NextMatchFragment> {
        override fun createView(ui: AnkoContext<NextMatchFragment>): View = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

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

                        owner.listNextEvent = recyclerView {
                            id = R.id.next_match_list
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initPresenter()

        getDataListOnSpinner()
        getDataOnSpinnerClicked()
        refreshSwipeRefresh()
    }

    private fun initAdapter() {
        adapter = MainMatchAdapter(events) { eventItem: Event -> listEventItemClicked(eventItem) }
        listNextEvent.adapter = adapter
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)
    }

    private fun initSpinner(leagues: List<Leagues>) {
        val spinnerAdapter = SpinnerAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        spinner.adapter = spinnerAdapter
    }

    private fun getDataListOnSpinner() {
        presenter.getLeaguesList()
    }

    private fun requestEventData(position: Int) {
        idLeague = leagues.get(position).idLeague.toString()
        presenter.getNextEventList(idLeague)
    }

    private fun getDataOnSpinnerClicked() {
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long) {
                requestEventData(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }

        }
    }

    private fun refreshSwipeRefresh() {
        swipeRefresh.onRefresh {
            events.clear()
            getDataListOnSpinner()
        }
    }

    override fun showLeagueList(data: List<Leagues>) {
        leagues.clear()
        leagues.addAll(data)

        initSpinner(data)
        requestEventData(spinner.selectedItemPosition)
    }

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false

        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun listEventItemClicked(eventItem: Event) {
        startActivity<MatchDetailActivity>(
                getString(R.string.intent_event) to eventItem
        )
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showError(ex: RuntimeException) {
        Toast.makeText(ctx, "Error Loading Data", Toast.LENGTH_LONG).show()
    }

}