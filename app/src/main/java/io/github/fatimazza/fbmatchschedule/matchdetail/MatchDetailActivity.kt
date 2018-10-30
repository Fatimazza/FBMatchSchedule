package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Team
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    var events: Event = Event()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtras()
        setContentView(R.layout.activity_detail)
        loadIntentExtras()
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

        Toast.makeText(this, "id ${events.idHomeTeam} AND ${events.idAwayTeam}", Toast.LENGTH_SHORT).show()

    }

    override fun showHomeTeamDetail(team: Team) {
        Toast.makeText(this, team.teamBadge, Toast.LENGTH_SHORT).show()
    }

    override fun showAwayTeamDetail(team: Team) {
        Toast.makeText(this, team.teamBadge, Toast.LENGTH_SHORT).show()
    }

}
