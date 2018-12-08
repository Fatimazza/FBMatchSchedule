package io.github.fatimazza.fbmatchschedule.main

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
import io.github.fatimazza.fbmatchschedule.model.Event
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class MainMatchAdapter(private val events: List<Event>,
                       private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(
                AnkoContext.create(parent.context, parent)
        ))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

}

class EventUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
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

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val matchDate: TextView = view.find(match_date)
    private val matchTime: TextView = view.find(match_time)
    private val homeTeam: TextView = view.find(home_team_name)
    private val matchScore: TextView = view.find(match_score)
    private val awayTeam: TextView = view.find(away_team_name)

    fun bindItem(events: Event, listener: (Event) -> Unit) {
        matchDate.text = SimpleDateFormat("EEE, dd MMM yyyy").format(events.dateEvent)
        matchTime.text = SimpleDateFormat("hh:mm").format(
                SimpleDateFormat("hh:mm:ssZ").parse(events.timeEvent))
        homeTeam.text = events.homeTeam
        matchScore.text = "${events.homeScore?: ""} vs ${events.awayScore?: ""}"
        awayTeam.text = events.awayTeam
        itemView.setOnClickListener { listener(events) }
    }

}