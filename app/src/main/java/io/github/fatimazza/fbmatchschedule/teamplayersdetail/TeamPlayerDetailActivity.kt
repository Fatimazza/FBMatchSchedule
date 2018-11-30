package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.fatimazza.fbmatchschedule.model.Players
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.setContentView

class TeamPlayerDetailActivity : AppCompatActivity(), TeamPlayerDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TeamPlayerDetailUI().setContentView(this)
    }

    class TeamPlayerDetailUI: AnkoComponent<TeamPlayerDetailActivity> {
        override fun createView(ui: AnkoContext<TeamPlayerDetailActivity>): View {
            return with(ui) {
                linearLayout {

                }
            }
        }
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPlayerDetail(data: List<Players>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
