package io.github.fatimazza.fbmatchschedule.matchdetail

import io.github.fatimazza.fbmatchschedule.model.Team
import java.lang.RuntimeException

interface MatchDetailView {

    fun showHomeTeamDetail(team: Team)

    fun showAwayTeamDetail(team: Team)

    fun showError(ex: RuntimeException)

}