package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.fatimazza.fbmatchschedule.R
import org.jetbrains.anko.*

class MatchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchDetailUI().setContentView(this)
    }

    class MatchDetailUI: AnkoComponent<MatchDetailActivity> {
        override fun createView(ui: AnkoContext<MatchDetailActivity>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    padding = dip(16)

                    view {
                        backgroundColor = resources.getColor(R.color.colorAccent)
                    }.lparams(width = matchParent, height = dip(1))
                }
            }
        }
    }
}
