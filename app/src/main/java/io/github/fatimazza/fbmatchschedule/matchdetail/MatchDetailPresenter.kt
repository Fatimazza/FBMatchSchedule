package io.github.fatimazza.fbmatchschedule.matchdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(idHomeTeam: String, idAwayTeam: String) {

        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                 gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getTeamDetail(idHomeTeam)),
                        TeamResponse::class.java
                )
            }
            view.showHomeTeamDetail(data.await().teams[0])
        }


        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getTeamDetail(idAwayTeam)),
                        TeamResponse::class.java
                )
            }
            view.showAwayTeamDetail(data.await().teams[0])
        }

    }

}