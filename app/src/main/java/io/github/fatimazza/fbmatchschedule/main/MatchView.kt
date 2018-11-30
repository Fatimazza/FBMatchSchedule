package io.github.fatimazza.fbmatchschedule.main

import io.github.fatimazza.fbmatchschedule.model.Event

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)

}