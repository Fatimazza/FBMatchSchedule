package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.id.add_to_favorite
import io.github.fatimazza.fbmatchschedule.R.menu.detail_menu
import io.github.fatimazza.fbmatchschedule.database.FavoriteMatch
import io.github.fatimazza.fbmatchschedule.database.database
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import java.sql.SQLClientInfoException
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
        events = intent.getParcelableExtra(getString(R.string.intent_event))
    }

    private fun loadIntentExtras() {

        tv_date.text = SimpleDateFormat(getString(R.string.date_format)).format(events.dateEvent)
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

    private fun addToFavorites() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.EVENT_ID to events.idEvent,
                        FavoriteMatch.EVENT_DATE to events.dateEvent,
                        FavoriteMatch.HOME_SCORE to events.homeScore,
                        FavoriteMatch.AWAY_SCORE to events.awayScore,
                        FavoriteMatch.HOME_TEAM to events.homeTeam,
                        FavoriteMatch.AWAY_TEAM to events.awayTeam)
            }
            snackbar(ll_team_detail, getString(R.string.favorite_added)).show()
        } catch (e: SQLClientInfoException) {
            snackbar(ll_team_detail, e.localizedMessage).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            add_to_favorite -> {
                addToFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
