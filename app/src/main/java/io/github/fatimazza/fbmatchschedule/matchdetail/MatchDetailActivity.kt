package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.fatimazza.fbmatchschedule.R
import kotlinx.android.synthetic.main.activity_detail.*

class MatchDetailActivity : AppCompatActivity() {

    var homeTeam: String = ""
    var awayTeam: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtras()
        setContentView(R.layout.activity_detail)
        loadIntentExtras()
    }

    private fun getIntentExtras() {
        val intent = intent
        homeTeam = intent.getStringExtra("homeTeam")
        awayTeam = intent.getStringExtra("awayTeam")
    }

    private fun loadIntentExtras() {
        tv_home_team.text = homeTeam
        tv_away_team.text = awayTeam
    }

}
