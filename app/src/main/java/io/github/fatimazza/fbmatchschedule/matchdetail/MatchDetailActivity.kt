package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.github.fatimazza.fbmatchschedule.R

class MatchDetailActivity : AppCompatActivity() {

    var homeTeam: String = ""
    var awayTeam: String = ""

    private lateinit var tvHomeTeam: TextView
    private lateinit var tvAwayTeam: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtras()
        setContentView(R.layout.activity_detail)
        initViews()
        loadIntentExtras()
    }

    private fun initViews() {
        tvHomeTeam = findViewById(R.id.tv_home_team)
        tvAwayTeam = findViewById(R.id.tv_away_team)
    }

    private fun getIntentExtras() {
        val intent = intent
        homeTeam = intent.getStringExtra("homeTeam")
        awayTeam = intent.getStringExtra("awayTeam")
    }

    private fun loadIntentExtras() {
        tvHomeTeam.text = homeTeam
        tvAwayTeam.text = awayTeam
    }

}
