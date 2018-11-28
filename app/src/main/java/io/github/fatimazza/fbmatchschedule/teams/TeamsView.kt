package io.github.fatimazza.fbmatchschedule.teams

import io.github.fatimazza.fbmatchschedule.model.Team

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)

}