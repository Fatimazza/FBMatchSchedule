package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Players
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.util.invisible
import io.github.fatimazza.fbmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamPlayerDetailActivity : AppCompatActivity(), TeamPlayerDetailView {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var playerFanart: ImageView
    private lateinit var playerDescription: TextView

    private lateinit var playerWeight: TextView
    private lateinit var playerHeight: TextView
    private lateinit var playerPosition: TextView

    private lateinit var presenter: TeamPlayerDetailPresenter

    private lateinit var id:String
    private lateinit var player: Players

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TeamPlayerDetailUI().setContentView(this)

        setupActionBar()
        getIntentExtra()

        initPresenter()
        requestDataTeamPlayerDetail()
        refreshSwipeRefresh()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Player Name"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class TeamPlayerDetailUI: AnkoComponent<TeamPlayerDetailActivity> {
        override fun createView(ui: AnkoContext<TeamPlayerDetailActivity>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL

                    owner.swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light)

                        scrollView {
                            isVerticalScrollBarEnabled = false
                            relativeLayout {
                                lparams(width = matchParent, height = wrapContent)

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)
                                    orientation = LinearLayout.VERTICAL

                                    owner.playerFanart = imageView() {
                                        setBackgroundColor(resources.getColor(R.color.colorAccent))
                                    }.lparams(width = matchParent, height = 450)

                                    owner.playerPosition = textView() {
                                        topPadding = dip(8)
                                        gravity = Gravity.CENTER_HORIZONTAL
                                    }

                                    linearLayout {
                                        lparams(width = matchParent, height = wrapContent)
                                        orientation = LinearLayout.HORIZONTAL
                                        topPadding = dip(8)

                                        textView {
                                            text = resources.getString(R.string.player_weight)
                                            gravity = Gravity.CENTER_HORIZONTAL
                                        }.lparams(width = dip(0), height = wrapContent, weight = 1.0f)
                                        textView {
                                            text = resources.getString(R.string.player_height)
                                            gravity = Gravity.CENTER_HORIZONTAL
                                        }.lparams(width = dip(0), height = wrapContent, weight = 1.0f)
                                    }

                                    linearLayout {
                                        lparams(width = matchParent, height = wrapContent)
                                        orientation = LinearLayout.HORIZONTAL

                                        owner.playerWeight = textView() {
                                            textSize = 20f
                                            gravity = Gravity.CENTER_HORIZONTAL
                                        }.lparams(width = dip(0), height = wrapContent, weight = 1.0f)
                                        owner.playerHeight = textView() {
                                            textSize = 20f
                                            gravity = Gravity.CENTER_HORIZONTAL
                                        }.lparams(width = dip(0), height = wrapContent, weight = 1.0f)
                                    }

                                    owner.playerDescription = textView()
                                            .lparams { margin = dip(8) }

                                }
                                owner.progressBar = progressBar()
                                        .lparams {
                                    centerHorizontally()
                                }
                            }
                        }
                        
                    }

                }
            }
        }
    }

    private fun getIntentExtra() {
        val intent = intent
        id = intent.getStringExtra(getString(R.string.intent_id))
    }

    private fun initPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPlayerDetailPresenter(this, request, gson)
    }

    private fun refreshSwipeRefresh() {
        swipeRefresh.onRefresh {
            requestDataTeamPlayerDetail()
        }
    }

    private fun requestDataTeamPlayerDetail() {
        presenter.getPlayerDetail(id)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerDetail(data: List<Players>) {
        swipeRefresh.isRefreshing = false

        player = Players(
                data[0].idPlayer,
                data[0].playerName,
                data[0].playerDesc,
                data[0].playerPosition,
                data[0].playerFanart,
                data[0].playerThumb,
                data[0].playerHeight,
                data[0].playerWeight)

        Picasso.get().load(data[0].playerThumb).into(playerFanart)

        playerWeight.text = data[0].playerWeight
        playerHeight.text = data[0].playerHeight
        playerPosition.text = data[0].playerPosition
        playerDescription.text = data[0].playerDesc

        supportActionBar?.title = data[0].playerName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
