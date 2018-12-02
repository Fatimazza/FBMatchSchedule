package io.github.fatimazza.fbmatchschedule.teams

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.LeaguesResponse
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeaguesList() {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getAllLeagues()),
                        LeaguesResponse::class.java)
            }
            view.showLeagueList(data.await().leagues)
            view.hideLoading()
        }

    }

    fun getTeamList(league: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java)
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }

    }
}

