package io.github.fatimazza.fbmatchschedule

import android.support.v7.widget.RecyclerView
import android.view.Gravity.*
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.github.fatimazza.fbmatchschedule.model.Event
import org.jetbrains.anko.*

class MainMatchAdapter(private val events: List<Event>)
    : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(
                AnkoContext.create(parent.context, parent)
        ))
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class EventUI(): AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                gravity = CENTER

                textView {
                    id = R.id.match_date
                    textSize = 14f
                    text = "Sat, 22 Sep 2008"
                }.lparams{
                    margin = dip(12)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    weightSum = 3f
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.home_team_name
                        text = "Team A"
                        textSize = 16f
                        gravity = RIGHT
                    }.lparams{
                        weight = 1f
                    }

                    textView {
                        id = R.id.match_score
                        text = "1 vs 1"
                        textSize = 16f
                        gravity = CENTER
                    }.lparams{
                        weight = 1f
                    }

                    textView {
                        id = R.id.away_team_name
                        text = "Team B"
                        textSize = 16f
                        gravity = LEFT
                    }.lparams{
                        weight = 1f
                    }
                }
            }
        }
    }
}

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(events: Event) {

    }

}