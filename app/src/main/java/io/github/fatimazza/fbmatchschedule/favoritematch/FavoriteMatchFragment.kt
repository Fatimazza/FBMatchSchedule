package io.github.fatimazza.fbmatchschedule.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.fatimazza.fbmatchschedule.main.MainMatchAdapter
import io.github.fatimazza.fbmatchschedule.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class FavoriteMatchFragment: Fragment() {

    private var events: MutableList<Event> = mutableListOf()

    private lateinit var adapter: MainMatchAdapter

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
        adapter = MainMatchAdapter(events) {  }
        listFavEvent.adapter = adapter
    }
}