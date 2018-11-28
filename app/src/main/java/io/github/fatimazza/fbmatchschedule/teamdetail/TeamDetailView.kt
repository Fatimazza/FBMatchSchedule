package io.github.fatimazza.fbmatchschedule.teamdetail

import io.github.fatimazza.fbmatchschedule.model.Team

interface TeamDetailView {

    fun showLoading()

    fun hideLoading()

    fun showTeamDetail(data: List<Team>)

}