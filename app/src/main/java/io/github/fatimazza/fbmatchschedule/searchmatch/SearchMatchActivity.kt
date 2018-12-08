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
import android.widget.Spinner
import io.github.fatimazza.fbmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchMatchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var searchItem: MenuItem? = null

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listNextEvent: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SearchMatchUI().setContentView(this)

        setupActionBar()
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
    }

    private fun setupActionBar() {
        supportActionBar?.title = getString(R.string.search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
}
