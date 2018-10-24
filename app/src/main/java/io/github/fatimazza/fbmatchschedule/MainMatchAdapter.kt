package io.github.fatimazza.fbmatchschedule

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.fatimazza.fbmatchschedule.model.Event
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout

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
            linearLayout {  }
        }
    }
}

class EventViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(events: Event) {

    }

}