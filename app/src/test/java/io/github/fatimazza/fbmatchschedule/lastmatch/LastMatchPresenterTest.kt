package io.github.fatimazza.fbmatchschedule.lastmatch

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.main.MatchPresenter
import io.github.fatimazza.fbmatchschedule.model.Event
import io.github.fatimazza.fbmatchschedule.model.EventResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.TestContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {

    @Mock
    private
    lateinit var view: LastMatchFragment

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        GlobalScope.launch {
            val data =  async {
                gson.fromJson(
                        apiRepository.doRequest(
                                TheSportDBApi.getLastMatch()), EventResponse::class.java)
            }
            `when`(data.await()).thenReturn(response)

            presenter.getLastEventList("4328")

            Mockito.verify(view).showEventList(events)
        }
    }
}