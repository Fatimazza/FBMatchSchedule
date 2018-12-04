package io.github.fatimazza.fbmatchschedule.nextmatch

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
import io.github.fatimazza.fbmatchschedule.main.MatchView
import io.github.fatimazza.fbmatchschedule.main.MainMatchAdapter
import io.github.fatimazza.fbmatchschedule.main.MatchPresenter
import io.github.fatimazza.fbmatchschedule.matchdetail.MatchDetailActivity
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class NextMatchFragment: Fragment(), MatchView {

    private var events: MutableList<Event> = mutableListOf()

    private lateinit var adapter: MainMatchAdapter

    private lateinit var presenter: MatchPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listNextEvent: RecyclerView

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

        swipeRefresh.isRefreshing = true
        requestEventData()

        swipeRefresh.onRefresh {
            events.clear()
            requestEventData()
        }
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

    private fun requestEventData() {
        presenter.getNextEventList()
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

}