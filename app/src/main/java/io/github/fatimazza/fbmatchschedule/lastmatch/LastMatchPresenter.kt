package io.github.fatimazza.fbmatchschedule.lastmatch

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LastMatchPresenter(private val view: LastMatchFragment,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventList() {

        GlobalScope.launch(context.main) {
            val data = async(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getLastMatch()),
                        EventResponse::class.java
                )
            }
            view.showEventList(data.await().events)
        }

    }

}