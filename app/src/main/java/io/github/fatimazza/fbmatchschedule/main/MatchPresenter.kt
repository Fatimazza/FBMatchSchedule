package io.github.fatimazza.fbmatchschedule.main

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.main.MatchView
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.model.LeaguesResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MatchPresenter(private val view: MatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeaguesList() {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(apiRepository.doRequest(
                        TheSportDBApi.getAllLeagues()),
                        LeaguesResponse::class.java)
            }
            view.showLeagueList(data.await().leagues)
            view.hideLoading()
        }

    }

    fun getLastEventList() {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getLastMatch()),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }

    }

    fun getNextEventList() {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getNextMatch()),
                        EventResponse::class.java
                )
            }
            view.showEventList(data.await().events)
            view.hideLoading()
        }

    }

}