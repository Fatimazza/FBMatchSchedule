package io.github.fatimazza.fbmatchschedule.teamplayers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.model.Players
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamsPlayersAdapter(private val players: List<Players>, private val listener:(Players) -> Unit)
    : RecyclerView.Adapter<TeamPlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayersViewHolder {
        return TeamPlayersViewHolder(TeamUI().createView(
                AnkoContext.create(parent.context, parent)
        ))
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: TeamPlayersViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

}

class TeamUI: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL
                weightSum = 5f

                imageView {
                    id = R.id.player_thumb
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                }.lparams(width= matchParent, height = wrapContent){
                    margin = dip(15)
                    weight = 2f
                }

                textView {
                    id = R.id.player_position
                }.lparams(width= matchParent, height = wrapContent){
                    margin = dip(15)
                    weight = 2f
                }

            }
        }
    }
}

class TeamPlayersViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val playerThumb: ImageView = view.find(R.id.player_thumb)
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerPosition: TextView = view.find(R.id.player_position)

    fun bindItem(players: Players, listener: (Players) -> Unit) {
        Picasso.get().load(players.playerThumb).into(playerThumb)
        playerName.text = players.playerName
        playerPosition.text = players.playerPosition
        itemView.onClick { listener(players) }
    }

}