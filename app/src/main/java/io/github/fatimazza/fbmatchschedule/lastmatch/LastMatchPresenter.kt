package io.github.fatimazza.fbmatchschedule.lastmatch

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LastMatchPresenter(private val view: LastMatchFragment,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson) {

    fun getEventList() {
        doAsync {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getLastMatch()),
                    EventResponse::class.java
            )
            uiThread {
                view.showEventList(data.events)
            }
        }
    }

}