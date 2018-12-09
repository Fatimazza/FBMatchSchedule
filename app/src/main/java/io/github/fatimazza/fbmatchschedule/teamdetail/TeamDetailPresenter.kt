package io.github.fatimazza.fbmatchschedule.teamdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.teamoverview.TeamOverviewView
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

class TeamDetailPresenter(private val view: TeamOverviewView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider())
    : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getTeamDetail(teamId: String) {

        CoroutineScope(context.main).launch {
            view.showLoading()
            try {
                val data = withContext(context.background) {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                            TeamResponse::class.java
                    )
                }
                view.showTeamDetail(data.teams)
            } catch (ex: RuntimeException) {
                view.showError(ex)
            }
            view.hideLoading()
        }

    }

}
