package io.github.fatimazza.fbmatchschedule.matchdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider())
    : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getTeamDetail(idHomeTeam: String, idAwayTeam: String) {

        CoroutineScope(context.main).launch {

            try {
                val data = withContext(context.background) {
                    gson.fromJson(
                            apiRepository.doRequest(TheSportDBApi.getTeamDetail(idHomeTeam)),
                            TeamResponse::class.java
                    )
                }
                view.showHomeTeamDetail(data.teams[0])
            } catch (ex: RuntimeException) {
                view.showError(ex)
            }

        }

        CoroutineScope(context.main).launch {

            try {
                val data = withContext(context.background) {
                    gson.fromJson(
                            apiRepository.doRequest(TheSportDBApi.getTeamDetail(idAwayTeam)),
                            TeamResponse::class.java
                    )
                }
                view.showAwayTeamDetail(data.teams[0])
            } catch (ex: RuntimeException) {
                view.showError(ex)
            }

        }

    }

}