package io.github.fatimazza.fbmatchschedule.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.github.fatimazza.fbmatchschedule.model.Leagues

class SpinnerAdapter(context:Context,
                     textViewResourceId: Int,
                     private val values: List<Leagues>)
    : ArrayAdapter<Leagues>(context, textViewResourceId, values) {

    override fun getItem(position: Int): Leagues {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return values.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup)
            : View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].strLeague
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup)
            : View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = values[position].strLeague
        return label
    }
}