package io.github.fatimazza.fbmatchschedule.teams

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.LeaguesResponse
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.*

import kotlin.coroutines.CoroutineContext

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider())
    : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getLeaguesList() {
        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getAllLeagues()),
                        LeaguesResponse::class.java)
            }
            view.showLeagueList(data.leagues)
            view.hideLoading()
        }
    }

    fun getTeamList(league: String?) {
        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java)
            }
            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }

    fun searchTeam(teamName: String?) {
        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.searchTeam(teamName)),
                        TeamResponse::class.java)
            }
            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }

}

