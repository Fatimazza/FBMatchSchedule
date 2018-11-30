package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.teamoverview.TeamOverviewView
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamPlayerDetailPresenter(private val view: TeamOverviewView,
                                private val apiRepository: ApiRepository,
                                private val gson: Gson,
                                private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(playerId: String) {

        view.showLoading()

        GlobalScope.launch(context.main){
            val data = async {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerDetail(playerId)),
                        TeamResponse::class.java
                )
            }
            view.showTeamDetail(data.await().teams)
            view.hideLoading()
        }

    }

}
