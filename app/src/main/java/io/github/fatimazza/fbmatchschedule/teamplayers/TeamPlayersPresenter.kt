package io.github.fatimazza.fbmatchschedule.teamplayers

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.PlayerResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.*

import kotlin.coroutines.CoroutineContext

class TeamPlayersPresenter(private val view: TeamPlayersView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider())
    : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getPlayersList(teamId: String?) {

        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getPlayer(teamId)),
                        PlayerResponse::class.java)
            }
            view.showPlayerList(data.player)
            view.hideLoading()
        }
        
    }
}

