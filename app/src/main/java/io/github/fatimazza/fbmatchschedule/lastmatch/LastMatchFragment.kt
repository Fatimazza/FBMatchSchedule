package io.github.fatimazza.fbmatchschedule.lastmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.MatchView
import io.github.fatimazza.fbmatchschedule.main.MainMatchAdapter
import io.github.fatimazza.fbmatchschedule.matchdetail.MatchDetailActivity
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class LastMatchFragment: Fragment(), MatchView {

    private var events: MutableList<Event> = mutableListOf()

    private lateinit var adapter: MainMatchAdapter

    private lateinit var presenter: LastMatchPresenter

    private lateinit var listLastEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return LastMatchFragmentUI()
                .createView(AnkoContext.create(ctx, this, false))
    }

    class LastMatchFragmentUI: AnkoComponent<LastMatchFragment> {
        override fun createView(ui: AnkoContext<LastMatchFragment>): View = with(ui) {
            frameLayout {
                owner.listLastEvent = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initPresenter()
        requestEventData()
    }

    private fun initAdapter() {
        adapter = MainMatchAdapter(events, { eventItem: Event -> listEventItemClicked(eventItem) })
        listLastEvent.adapter = adapter
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = LastMatchPresenter(this, request, gson)
    }

    private fun requestEventData() {
        presenter.getEventList()
    }

    override fun showEventList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun listEventItemClicked(eventItem: Event) {
       startActivity<MatchDetailActivity>(
               "homeTeam" to "${eventItem.homeTeam}",
               "awayTeam" to "${eventItem.awayTeam}"
       )
    }

}
