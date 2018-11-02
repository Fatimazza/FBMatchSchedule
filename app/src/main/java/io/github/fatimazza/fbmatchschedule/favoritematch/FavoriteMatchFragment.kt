package io.github.fatimazza.fbmatchschedule.favoritematch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout

class FavoriteMatchFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    class FavoriteMatchUI: AnkoComponent<FavoriteMatchFragment> {

        override fun createView(ui: AnkoContext<FavoriteMatchFragment>): View {
            return with(ui) {
                frameLayout {

                }
            }
        }

    }
}