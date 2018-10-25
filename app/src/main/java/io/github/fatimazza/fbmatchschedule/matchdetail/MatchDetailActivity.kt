package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.setContentView

class MatchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchDetailUI().setContentView(this)
    }
}

class MatchDetailUI: AnkoComponent<MatchDetailActivity> {
    override fun createView(ui: AnkoContext<MatchDetailActivity>): View {
        return with(ui) {
            linearLayout {  }
        }
    }
}
