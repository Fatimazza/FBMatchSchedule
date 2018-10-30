package io.github.fatimazza.fbmatchschedule.matchdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getTeamDetail(idHomeTeam: String, idAwayTeam: String) {
        
        doAsync {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamDetail(idHomeTeam)),
                    TeamResponse::class.java
            )
            uiThread {
            }
        }

        doAsync {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamDetail(idAwayTeam)),
                    TeamResponse::class.java
            )
            uiThread {
            }
        }
    }

}