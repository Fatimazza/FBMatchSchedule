package io.github.fatimazza.fbmatchschedule.main

import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.Leagues
import java.lang.RuntimeException

interface MatchView {

    fun showLoading()
    fun hideLoading()
    fun showError(ex: RuntimeException)
    fun showLeagueList(data: List<Leagues>)
    fun showEventList(data: List<Event>)

}