package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    private var events: Event = Event()

    private lateinit var presenter: MatchDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtras()
        setContentView(R.layout.activity_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        loadIntentExtras()

        initPresenter()
        requestDataTeamDetail()
    }

    private fun getIntentExtras() {
        events = intent.getParcelableExtra<Event>("event")
    }

    private fun loadIntentExtras() {

        tv_date.text = SimpleDateFormat("EEE, dd MMM yyyy").format(events.dateEvent)
        tv_home_team.text = events.homeTeam
        tv_away_team.text = events.awayTeam

        tv_score.text = "${events.homeScore?: ""} vs ${events.awayScore?: ""}"

        tv_goal_home.text = events.homeGoals
        tv_goal_away.text = events.awayGoals

        tv_shot_home.text = "${events.homeShots?: ""}"
        tv_shot_away.text = "${events.awayShots?: ""}"

        tv_goalkeeper_home.text = events.homeGoalkeeper
        tv_goalkeeper_away.text = events.awayGoalkeeper

        tv_defense_home.text = events.homeDefense
        tv_defense_away.text = events.awayDefense

        tv_midfield_home.text = events.homeMidfield
        tv_midfield_away.text = events.awayMidfield

        tv_forward_home.text = events.homeForward
        tv_forward_away.text = events.awayForward

        tv_subtitues_home.text = events.homeSubtitues
        tv_subtitues_away.text = events.awaySubtitues
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)
    }

    private fun requestDataTeamDetail() {
        presenter.getTeamDetail(events.idHomeTeam.toString(), events.idAwayTeam.toString())
    }

    override fun showHomeTeamDetail(team: Team) {
        Picasso.get().load(team.teamBadge).into(iv_home_team)
    }

    override fun showAwayTeamDetail(team: Team) {
        Picasso.get().load(team.teamBadge).into(iv_away_team)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
