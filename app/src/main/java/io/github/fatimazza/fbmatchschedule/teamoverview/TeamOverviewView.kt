package io.github.fatimazza.fbmatchschedule.teamoverview

import io.github.fatimazza.fbmatchschedule.model.Team

interface TeamOverviewView {

    fun showLoading()

    fun hideLoading()

    fun showTeamDetail(data: List<Team>)

}