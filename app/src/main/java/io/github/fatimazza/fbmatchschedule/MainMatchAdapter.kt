package io.github.fatimazza.fbmatchschedule

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.fatimazza.fbmatchschedule.model.Event

class MainMatchAdapter(private val events: List<Event>)
    : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(events: Event) {

    }

}