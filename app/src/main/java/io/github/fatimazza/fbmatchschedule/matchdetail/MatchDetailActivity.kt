package io.github.fatimazza.fbmatchschedule.matchdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.drawable.ic_add_to_favorite
import io.github.fatimazza.fbmatchschedule.R.drawable.ic_added_to_favorite
import io.github.fatimazza.fbmatchschedule.R.id.add_to_favorite
import io.github.fatimazza.fbmatchschedule.R.menu.detail_menu
import io.github.fatimazza.fbmatchschedule.database.FavoriteMatch
import io.github.fatimazza.fbmatchschedule.database.database
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.util.isOnline
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.lang.RuntimeException
import java.sql.SQLClientInfoException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    private lateinit var events: Event

    private lateinit var favorites: FavoriteMatch

    private lateinit var presenter: MatchDetailPresenter


    private var isFavorite: Boolean = false

    private lateinit var id:String


    private var menuItem: Menu? = null

    private var isFavoriteMatch: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtras()
        setContentView(R.layout.activity_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (isFavorite) loadIntentFavoriteExtras() else loadIntentEventExtras()
        favoriteState()

        initPresenter()
        requestDataTeamDetail()
    }

    private fun getIntentExtras() {
        if (intent.hasExtra(getString(R.string.intent_event))) {
            events = intent.getParcelableExtra(getString(R.string.intent_event))
        } else {
            favorites = intent.getParcelableExtra(getString(R.string.intent_favorite))
            isFavorite = true
        }
    }

    private fun loadIntentEventExtras() {

        id = events.idEvent.toString()

        tv_date.text = SimpleDateFormat(getString(R.string.date_format)).format(events.dateEvent)

        if (events.timeEvent != null) {
            try {
                tv_time.text = SimpleDateFormat("HH:mm").format(
                        SimpleDateFormat("hh:mm:ssZ").parse(events.timeEvent))
            } catch (ex: ParseException) {
                ex.printStackTrace();
                tv_time.text = SimpleDateFormat("HH:mm").format(
                        SimpleDateFormat("hh:mm:ss").parse(events.timeEvent))
            }
        } else {
            tv_time.text = ""
        }

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

    private fun loadIntentFavoriteExtras() {

        id = favorites.eventId.toString()

        tv_date.text = SimpleDateFormat(getString(R.string.date_format))
                .format(Date(favorites.eventDate))

        if (favorites.eventTime != null) {
            try {
                tv_time.text = SimpleDateFormat("HH:mm").format(
                        SimpleDateFormat("hh:mm:ssZ").parse(favorites.eventTime))
            } catch (ex: ParseException) {
                ex.printStackTrace();
                tv_time.text = SimpleDateFormat("HH:mm").format(
                        SimpleDateFormat("hh:mm:ss").parse(favorites.eventTime))
            }
        } else {
            tv_time.text=""
        }


        tv_home_team.text = favorites.homeTeam
        tv_away_team.text = favorites.awayTeam

        tv_score.text = "${favorites.homeScore?: ""} vs ${favorites.awayScore?: ""}"

        tv_goal_home.text = favorites.homeGoals
        tv_goal_away.text = favorites.awayGoals

        tv_shot_home.text = "${favorites.homeShots?: ""}"
        tv_shot_away.text = "${favorites.awayShots?: ""}"

        tv_goalkeeper_home.text = favorites.homeKeeper
        tv_goalkeeper_away.text = favorites.homeKeeper

        tv_defense_home.text = favorites.homeDefense
        tv_defense_away.text = favorites.awayDefense

        tv_midfield_home.text = favorites.homeMidfield
        tv_midfield_away.text = favorites.awayMidfield

        tv_forward_home.text = favorites.homeForward
        tv_forward_away.text = favorites.awayForward

        tv_subtitues_home.text = favorites.homeSubtitues
        tv_subtitues_away.text = favorites.awaySubtitues

    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)
    }

    private fun requestDataTeamDetail() {
        if (isFavorite) {
            if (isOnline(this)) presenter.getTeamDetail(favorites.homeTeamId.toString(), favorites.awayTeamId.toString())
        }
        else {
            if (isOnline(this)) presenter.getTeamDetail(events.idHomeTeam.toString(), events.idAwayTeam.toString())
        }
    }

    override fun showHomeTeamDetail(team: Team) {
        Picasso.get().load(team.teamBadge).into(iv_home_team)
    }

    override fun showAwayTeamDetail(team: Team) {
        Picasso.get().load(team.teamBadge).into(iv_away_team)
    }

    private fun addToFavorites() {
        if (isFavorite)
            addFavoriteToFavorites()
        else addEventToFavorites()
    }

    private fun addEventToFavorites() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.EVENT_ID to events.idEvent,
                        FavoriteMatch.EVENT_DATE to events.dateEvent.toString(),
                        FavoriteMatch.EVENT_TIME to events.timeEvent,

                        FavoriteMatch.HOME_TEAM_ID to events.idHomeTeam,
                        FavoriteMatch.AWAY_TEAM_ID to events.idAwayTeam,

                        FavoriteMatch.HOME_TEAM to events.homeTeam,
                        FavoriteMatch.HOME_SCORE to events.homeScore,
                        FavoriteMatch.HOME_GOALS to events.homeGoals,
                        FavoriteMatch.HOME_SHOTS to events.homeShots,

                        FavoriteMatch.HOME_KEEPER to events.homeGoalkeeper,
                        FavoriteMatch.HOME_DEFENSE to events.homeDefense,
                        FavoriteMatch.HOME_MIDFIELD to events.homeMidfield,
                        FavoriteMatch.HOME_FORWARD to events.homeForward,
                        FavoriteMatch.HOME_SUBTITUES to events.homeSubtitues,

                        FavoriteMatch.AWAY_TEAM to events.awayTeam,
                        FavoriteMatch.AWAY_SCORE to events.awayScore,
                        FavoriteMatch.AWAY_GOALS to events.awayGoals,
                        FavoriteMatch.AWAY_SHOTS to events.awayShots,

                        FavoriteMatch.AWAY_KEEPER to events.awayGoalkeeper,
                        FavoriteMatch.AWAY_DEFENSE to events.awayDefense,
                        FavoriteMatch.AWAY_MIDFIELD to events.awayMidfield,
                        FavoriteMatch.AWAY_FORWARD to events.awayForward,
                        FavoriteMatch.AWAY_SUBTITUES to events.awaySubtitues)
            }
            snackbar(ll_team_detail, getString(R.string.favorite_added)).show()
        } catch (e: SQLClientInfoException) {
            snackbar(ll_team_detail, e.localizedMessage).show()
        }
    }

    private fun addFavoriteToFavorites() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE,
                        FavoriteMatch.EVENT_ID to favorites.eventId,
                        FavoriteMatch.EVENT_DATE to favorites.eventDate,
                        FavoriteMatch.EVENT_TIME to favorites.eventTime,

                        FavoriteMatch.HOME_TEAM_ID to favorites.homeTeamId,
                        FavoriteMatch.AWAY_TEAM_ID to favorites.awayTeamId,

                        FavoriteMatch.HOME_TEAM to favorites.homeTeam,
                        FavoriteMatch.HOME_SCORE to favorites.homeScore,
                        FavoriteMatch.HOME_GOALS to favorites.homeGoals,
                        FavoriteMatch.HOME_SHOTS to favorites.homeShots,

                        FavoriteMatch.HOME_KEEPER to favorites.homeKeeper,
                        FavoriteMatch.HOME_DEFENSE to favorites.homeDefense,
                        FavoriteMatch.HOME_MIDFIELD to favorites.homeMidfield,
                        FavoriteMatch.HOME_FORWARD to favorites.homeForward,
                        FavoriteMatch.HOME_SUBTITUES to favorites.homeSubtitues,

                        FavoriteMatch.AWAY_TEAM to favorites.awayTeam,
                        FavoriteMatch.AWAY_SCORE to favorites.awayScore,
                        FavoriteMatch.AWAY_GOALS to favorites.awayGoals,
                        FavoriteMatch.AWAY_SHOTS to favorites.awayShots,

                        FavoriteMatch.AWAY_KEEPER to favorites.awayKeeper,
                        FavoriteMatch.AWAY_DEFENSE to favorites.awayDefense,
                        FavoriteMatch.AWAY_MIDFIELD to favorites.awayMidfield,
                        FavoriteMatch.AWAY_FORWARD to favorites.awayForward,
                        FavoriteMatch.AWAY_SUBTITUES to favorites.awaySubtitues)
            }
            snackbar(ll_team_detail, getString(R.string.favorite_added)).show()
        } catch (e: SQLClientInfoException) {
            snackbar(ll_team_detail, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorites() {
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE,
                        "(EVENT_ID = {id})",
                        "id" to id)
            }
            snackbar(ll_team_detail, getString(R.string.favorite_removed)).show()
        } catch (e: SQLClientInfoException) {
            snackbar(ll_team_detail, e.localizedMessage).show()
        }
    }

    private fun setFavoriteIcon() {
        if (isFavoriteMatch)
            menuItem?.getItem(0)?.icon =
                    ContextCompat.getDrawable(this, ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon =
                    ContextCompat.getDrawable(this, ic_add_to_favorite)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})", "id" to id)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            if (!favorite.isEmpty())
                isFavoriteMatch = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            add_to_favorite -> {
                if (isFavoriteMatch) removeFromFavorites() else addToFavorites()
                isFavoriteMatch = !isFavoriteMatch
                setFavoriteIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showError(ex: RuntimeException) {
        Toast.makeText(this, "Error Loading Data", Toast.LENGTH_LONG).show()
    }
}
