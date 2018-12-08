package io.github.fatimazza.fbmatchschedule.searchmatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.main.MainMatchAdapter
import io.github.fatimazza.fbmatchschedule.main.MatchView
import io.github.fatimazza.fbmatchschedule.matchdetail.MatchDetailActivity
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Leagues
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchMatchActivity : AppCompatActivity(), MatchView, SearchView.OnQueryTextListener {

    private var searchItem: MenuItem? = null

    private var events: MutableList<Event> = mutableListOf()

    private lateinit var adapter: MainMatchAdapter

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listEvent: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SearchMatchUI().setContentView(this)
        setupActionBar()

        initAdapter()
    }

    class SearchMatchUI: AnkoComponent<SearchMatchActivity> {
        override fun createView(ui: AnkoContext<SearchMatchActivity>): View {
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

                            owner.listEvent = recyclerView {
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
    }

    private fun setupActionBar() {
        supportActionBar?.title = getString(R.string.search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        adapter = MainMatchAdapter(events) { eventItem: Event -> listEventItemClicked(eventItem) }
        listEvent.adapter = adapter
    }

    private fun listEventItemClicked(eventItem: Event) {
        startActivity<MatchDetailActivity>(
                getString(R.string.intent_event) to eventItem
        )
    }

    override fun onResume() {
        super.onResume()
        if (null != searchItem)
            searchItem?.collapseActionView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.searchview_menu, menu)

        searchItem = menu.findItem(R.id.searchview)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (!query.isNullOrBlank()) {  }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueList(data: List<Leagues>) {
    }

    override fun showEventList(data: List<Event>) {
    }
}
