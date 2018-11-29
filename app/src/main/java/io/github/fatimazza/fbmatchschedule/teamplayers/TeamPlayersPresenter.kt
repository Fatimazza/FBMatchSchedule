package io.github.fatimazza.fbmatchschedule.teamplayers

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.PlayerResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamPlayersPresenter(private val view: TeamPlayersView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayersList(teamId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getPlayer(teamId)),
                        PlayerResponse::class.java)
            }
            view.showPlayerList(data.await().players)
            view.hideLoading()
        }

    }
}

