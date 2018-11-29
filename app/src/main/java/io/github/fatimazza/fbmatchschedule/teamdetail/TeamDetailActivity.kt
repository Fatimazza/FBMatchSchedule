package io.github.fatimazza.fbmatchschedule.teamdetail


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.drawable.ic_add_to_favorite
import io.github.fatimazza.fbmatchschedule.R.drawable.ic_added_to_favorite
import io.github.fatimazza.fbmatchschedule.R.id.add_to_favorite
import io.github.fatimazza.fbmatchschedule.R.menu.detail_menu
import io.github.fatimazza.fbmatchschedule.database.FavoriteTeam
import io.github.fatimazza.fbmatchschedule.database.database
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.teamoverview.TeamOverviewView
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.sql.SQLClientInfoException

class TeamDetailActivity : AppCompatActivity(), TeamOverviewView {

    private lateinit var presenter: TeamDetailPresenter

    private lateinit var team: Team
    private lateinit var id:String
    private var isFavorite: Boolean = false

    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setupActionBar()
        getIntentExtra()

        initPagerAdapter()
        favoriteState()

        initPresenter()
        requestDataTeamDetail()
        swipe_team_detail.onRefresh {
            requestDataTeamDetail()
        }
    }

    private fun getIntentExtra() {
        val intent = intent
        id = intent.getStringExtra(getString(R.string.intent_id))
    }

    private fun setupActionBar() {
        supportActionBar?.title = getString(R.string.team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPagerAdapter() {
        vp_team_detail.adapter = TeamDetailPagerAdapter(supportFragmentManager, ctx, id)
        tab_team.setupWithViewPager(vp_team_detail)
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
    }

    private fun requestDataTeamDetail() {
        presenter.getTeamDetail(id)
    }

    override fun showLoading() {
        progress_team.visible()
    }

    override fun hideLoading() {
        progress_team.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        team = Team(data[0].teamId, data[0].teamName, data[0].teamBadge)
        swipe_team_detail.isRefreshing = false

        Picasso.get().load(data[0].teamBadge).into(team_badge)
        team_name.text = data[0].teamName
        team_year.text = data[0].teamFormedYear
        team_stadium.text = data[0].teamStadium
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to team.teamId,
                        FavoriteTeam.TEAM_NAME to team.teamName,
                        FavoriteTeam.TEAM_BADGE to team.teamBadge)
            }
            snackbar(swipe_team_detail, getString(R.string.favorite_added)).show()
        }
        catch (e: SQLClientInfoException) {
            snackbar(swipe_team_detail, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(swipe_team_detail, getString(R.string.favorite_removed)).show()
        } catch (e: SQLClientInfoException) {
            snackbar(swipe_team_detail, e.localizedMessage).show()
        }
    }

    private fun setFavoriteIcon() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                    ContextCompat.getDrawable(this, ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon =
                    ContextCompat.getDrawable(this, ic_add_to_favorite)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavoriteIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
