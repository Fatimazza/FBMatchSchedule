package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.PlayerResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamPlayerDetailPresenter(private val view: TeamPlayerDetailView,
                                private val apiRepository: ApiRepository,
                                private val gson: Gson,
                                private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerDetail(playerId: String) {

        view.showLoading()

        GlobalScope.launch(context.main){
            val data = async {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayerDetail(playerId)),
                        PlayerResponse::class.java
                )
            }
            view.showPlayerDetail(data.await().player)
            view.hideLoading()
        }

    }

}
