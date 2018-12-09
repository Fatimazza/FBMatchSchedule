package io.github.fatimazza.fbmatchschedule.main

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.model.LeaguesResponse
import io.github.fatimazza.fbmatchschedule.model.EventSearchResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MatchPresenter(private val view: MatchView,
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

    fun searchMatch(eventName: String?) {
        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.searchMatch(eventName)),
                        EventSearchResponse::class.java
                )
            }
            view.showEventList(data.event)
            view.hideLoading()
        }
    }

    fun getLastEventList(idLeague: String?) {
        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getLastMatch(idLeague)),
                        EventResponse::class.java
                )
            }
            view.showEventList(data.events)
            view.hideLoading()
        }
    }

    fun getNextEventList(idLeague: String?) {
        CoroutineScope(context.main).launch {
            view.showLoading()
            val data = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getNextMatch(idLeague)),
                        EventResponse::class.java
                )
            }
            view.showEventList(data.events)
            view.hideLoading()
        }
    }
}