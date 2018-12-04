package io.github.fatimazza.fbmatchschedule.main

import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Leagues

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<Leagues>)
    fun showEventList(data: List<Event>)

}