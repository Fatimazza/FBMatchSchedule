package io.github.fatimazza.fbmatchschedule.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.color.colorAccent
import io.github.fatimazza.fbmatchschedule.database.FavoriteMatch
import io.github.fatimazza.fbmatchschedule.database.database
import io.github.fatimazza.fbmatchschedule.matchdetail.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteMatchFragment: Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()

    private lateinit var adapter: FavoriteMatchAdapter

    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var listFavEvent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return FavoriteMatchUI().createView(
                AnkoContext.create(ctx, this, false))
    }

    class FavoriteMatchUI: AnkoComponent<FavoriteMatchFragment> {

        override fun createView(ui: AnkoContext<FavoriteMatchFragment>): View {
            return with(ui) {
                frameLayout {

                    owner.swipeRefresh = swipeRefreshLayout {
                        id = R.id.swipe_refresh_fav_match
                        setColorSchemeResources(colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light)

                        owner.listFavEvent = recyclerView {
                            id = R.id.fav_match_list
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        swipeRefresh.isRefreshing = true
        showFavorite()

        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun initAdapter() {
        adapter = FavoriteMatchAdapter(favorites) { favItem: FavoriteMatch -> listFavItemClicked(favItem) }
        listFavEvent.adapter = adapter
    }

    private fun listFavItemClicked(favItem: FavoriteMatch) {
        startActivity<MatchDetailActivity>(
                getString(R.string.intent_favorite) to favItem
        )
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false

            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)

            adapter.notifyDataSetChanged()
        }
    }


}