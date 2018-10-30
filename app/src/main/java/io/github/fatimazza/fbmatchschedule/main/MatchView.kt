package io.github.fatimazza.fbmatchschedule.main

import io.github.fatimazza.fbmatchschedule.model.Event

interface MatchView {

    fun showEventList(data: List<Event>)

}