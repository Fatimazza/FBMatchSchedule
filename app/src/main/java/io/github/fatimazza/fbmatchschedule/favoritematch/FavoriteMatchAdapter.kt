package io.github.fatimazza.fbmatchschedule.favoritematch

import android.graphics.Typeface.BOLD
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity.*
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.id.*
import io.github.fatimazza.fbmatchschedule.database.FavoriteMatch
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMatchAdapter(private val favorites: List<FavoriteMatch>,
                           private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavoriteUI().createView(
                AnkoContext.create(parent.context, parent)
        ))
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorites[position], listener)
    }

}

class FavoriteUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                id = R.id.favorite_item
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                gravity = CENTER
                backgroundDrawable = ctx.resources.getDrawable(R.drawable.recycler_view_selector)

                textView {
                    id = R.id.match_date
                    textSize = 14f
                    text = context.getString(R.string.dummy_date)
                }.lparams{
                    margin = dip(12)
                }

                textView {
                    id = R.id.match_time
                    textSize = 14f
                    text = context.getString(R.string.dummy_time)
                }.lparams{
                    bottomMargin = dip(12)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    weightSum = 3f
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.home_team_name
                        text = context.getString(R.string.dummy_home_team)
                        textSize = 16f
                        maxLines = 1
                        maxWidth = 5
                        ellipsize = TextUtils.TruncateAt.END
                        gravity = END
                    }.lparams{
                        weight = 1f
                    }

                    textView {
                        id = R.id.match_score
                        text = context.getString(R.string.dummy_score)
                        setTypeface(typeface, BOLD)
                        textSize = 16f
                        maxLines = 1
                        maxWidth = 5
                        gravity = CENTER
                    }.lparams{
                        weight = 1f
                    }

                    textView {
                        id = R.id.away_team_name
                        text = context.getString(R.string.dummy_away_team)
                        textSize = 16f
                        maxLines = 1
                        maxWidth = 5
                        ellipsize = TextUtils.TruncateAt.END
                        gravity = START
                    }.lparams{
                        weight = 1f
                    }
                }
            }
        }
    }
}

class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val matchDate: TextView = view.find(match_date)
    private val matchTime: TextView = view.find(match_time)
    private val homeTeam: TextView = view.find(home_team_name)
    private val matchScore: TextView = view.find(match_score)
    private val awayTeam: TextView = view.find(away_team_name)

    fun bindItem(favorites: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {
        matchDate.text = SimpleDateFormat("EEE, dd MMM yyyy").format(Date(favorites.eventDate))

        if (favorites.eventTime != null) {
            try {
                matchTime.text = SimpleDateFormat("HH:mm").format(
                        SimpleDateFormat("hh:mm:ssZ").parse(favorites.eventTime))
            } catch (ex: ParseException) {
                ex.printStackTrace();
                matchTime.text = SimpleDateFormat("HH:mm").format(
                        SimpleDateFormat("hh:mm:ss").parse(favorites.eventTime))
            }
        } else {
            matchTime.text=""
        }

        homeTeam.text = favorites.homeTeam
        matchScore.text = "${favorites.homeScore?: ""} vs ${favorites.awayScore?: ""}"
        awayTeam.text = favorites.awayTeam
        itemView.setOnClickListener { listener(favorites) }
    }

}