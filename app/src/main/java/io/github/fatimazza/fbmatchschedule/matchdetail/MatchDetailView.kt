package io.github.fatimazza.fbmatchschedule.matchdetail

import io.github.fatimazza.fbmatchschedule.model.Team

interface MatchDetailView {

    fun showHomeTeamDetail(team: Team)

    fun showAwayTeamDetail(team: Team)

}