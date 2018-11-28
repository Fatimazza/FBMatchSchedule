package io.github.fatimazza.fbmatchschedule.teamdetail

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.sql.SQLClientInfoException

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var presenter: TeamDetailPresenter

    private lateinit var team: Team
    private lateinit var id:String
    private var isFavorite: Boolean = false

    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TeamDetailUI().setContentView(this)
        setupActionBar()
        getIntentExtra()

        favoriteState()
        initPresenter()
        requestDataTeamDetail()
        swipeRefresh.onRefresh {
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

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
    }

    private fun requestDataTeamDetail() {
        presenter.getTeamDetail(id)
    }

    class TeamDetailUI: AnkoComponent<TeamDetailActivity> {
        override fun createView(ui: AnkoContext<TeamDetailActivity>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = Color.WHITE

                    owner.swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(
                                R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light
                        )

                        scrollView {
                            isVerticalScrollBarEnabled = false
                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)
                                    orientation = LinearLayout.VERTICAL
                                    gravity = Gravity.CENTER_HORIZONTAL

                                    owner.teamBadge = imageView{}
                                            .lparams(height = dip(75))

                                    owner.teamName = textView {
                                        this.gravity = Gravity.CENTER
                                        textSize = 20f
                                        textColor = ContextCompat.getColor(
                                                context, R.color.colorAccent
                                        )
                                    }.lparams{ topMargin = dip(5) }

                                    owner.teamFormedYear = textView {
                                        this.gravity= Gravity.CENTER
                                    }

                                    owner.teamStadium = textView {
                                        this.gravity = Gravity.CENTER
                                        textColor = ContextCompat.getColor(
                                                context, R.color.colorPrimaryDark
                                        )
                                    }

                                    owner.teamDescription = textView()
                                            .lparams{topMargin = dip(20)}

                                }

                                owner.progressBar = progressBar {  }
                                        .lparams{ centerHorizontally() }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {

        team = Team(data[0].teamId, data[0].teamName, data[0].teamBadge)
        swipeRefresh.isRefreshing = false

        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium
        
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to team.teamId,
                        FavoriteTeam.TEAM_NAME to team.teamName,
                        FavoriteTeam.TEAM_BADGE to team.teamBadge)
            }
            snackbar(swipeRefresh, getString(R.string.favorite_added)).show()
        }
        catch (e: SQLClientInfoException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, getString(R.string.favorite_removed)).show()
        } catch (e: SQLClientInfoException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
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
