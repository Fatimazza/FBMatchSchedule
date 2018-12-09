package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.PlayerDetailResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

class TeamPlayerDetailPresenter(private val view: TeamPlayerDetailView,
                                private val apiRepository: ApiRepository,
                                private val gson: Gson,
                                private val context: CoroutineContextProvider = CoroutineContextProvider())
    : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getPlayerDetail(playerId: String) {

        CoroutineScope(context.main).launch {
            view.showLoading()
            try {
                val data = withContext(context.background) {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getPlayerDetail(playerId)),
                            PlayerDetailResponse::class.java
                    )
                }
                view.showPlayerDetail(data.players)
            } catch (ex: RuntimeException) {
                view.showError(ex)
            }
            view.hideLoading()
        }

    }

}
