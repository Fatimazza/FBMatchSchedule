package io.github.fatimazza.fbmatchschedule.nextmatch

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(private val view: NextMatchFragment,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventList() {

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getNextMatch()),
                    EventResponse::class.java
            )

            view.showEventList(data.events)
        }

    }

}