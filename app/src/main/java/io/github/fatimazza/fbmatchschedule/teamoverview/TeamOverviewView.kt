package io.github.fatimazza.fbmatchschedule.teamoverview

import io.github.fatimazza.fbmatchschedule.model.Team
import java.lang.RuntimeException

interface TeamOverviewView {

    fun showLoading()

    fun hideLoading()

    fun showError(ex: RuntimeException)

    fun showTeamDetail(data: List<Team>)

}