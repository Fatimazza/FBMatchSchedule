package io.github.fatimazza.fbmatchschedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class NextMatchFragment: Fragment() {

    private var events: MutableList<Event> = mutableListOf()

    private lateinit var adapter: MainMatchAdapter

    private lateinit var listNextEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return NextMatchFragmentUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class NextMatchFragmentUI: AnkoComponent<NextMatchFragment> {
        override fun createView(ui: AnkoContext<NextMatchFragment>): View = with(ui) {
            frameLayout {
                owner.listNextEvent = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        requestData()
    }

    private fun initAdapter() {
        adapter = MainMatchAdapter(events)
        listNextEvent.adapter = adapter
    }

    private fun requestData() {
        val request = ApiRepository()
        val gson = Gson()
        //doInPresenter
        doAsync {
            val data = gson.fromJson(request.doRequest(
                    TheSportDBApi.getNextMatch()),
                    EventResponse::class.java)
            uiThread {
                showEventList(data.events)
            }
        }
    }

    private fun showEventList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}