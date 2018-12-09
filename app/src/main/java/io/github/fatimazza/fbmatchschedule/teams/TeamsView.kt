package io.github.fatimazza.fbmatchschedule.teams

import io.github.fatimazza.fbmatchschedule.model.Leagues
import io.github.fatimazza.fbmatchschedule.model.Team
import java.lang.RuntimeException

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun showError(ex: RuntimeException)
    fun showLeagueList(data: List<Leagues>)
    fun showTeamList(data: List<Team>)
}