package io.github.fatimazza.fbmatchschedule.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.database.FavoriteMatch
import io.github.fatimazza.fbmatchschedule.database.database
import io.github.fatimazza.fbmatchschedule.matchdetail.MatchDetailActivity
import io.github.fatimazza.fbmatchschedule.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMatchFragment: Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()

    private lateinit var adapter: FavoriteMatchAdapter

    private lateinit var listFavEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return FavoriteMatchUI().createView(
                AnkoContext.Companion.create(ctx, this, false))
    }

    class FavoriteMatchUI: AnkoComponent<FavoriteMatchFragment> {

        override fun createView(ui: AnkoContext<FavoriteMatchFragment>): View {
            return with(ui) {
                frameLayout {
                    owner.listFavEvent = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = FavoriteMatchAdapter(favorites) { eventItem: FavoriteMatch -> listEventItemClicked(eventItem) }
        listFavEvent.adapter = adapter

        showFavorite()
//        swipeRefresh.onRefresh {
//            favorites.clear()
//            showFavorite()
//        }

    }

    private fun listEventItemClicked(eventItem: FavoriteMatch) {
        startActivity<MatchDetailActivity>(
                getString(R.string.intent_event) to eventItem
        )
    }

    private fun showFavorite() {
        context?.database?.use {
//            swipeRefresh.isRefreshing = false

            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)

            adapter.notifyDataSetChanged()
        }
    }


}