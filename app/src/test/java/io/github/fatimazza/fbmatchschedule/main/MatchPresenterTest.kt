package io.github.fatimazza.fbmatchschedule.main

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.*
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.TestContextProvider
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var context: TestContextProvider

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        context = TestContextProvider()
        presenter = MatchPresenter(view, apiRepository, gson, context)
    }

    @Test
    fun testGetLeagueList() {
        val leagues: MutableList<Leagues> = mutableListOf()
        val response = LeaguesResponse(leagues)

        CoroutineScope(context.main).launch {
            val data = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(
                                TheSportDBApi.getAllLeagues()), LeaguesResponse::class.java)
            }
            `when`(data).thenReturn(response)

            presenter.getLeaguesList()

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueList(leagues)
            Mockito.verify(view).hideLoading()
        }

    }

    @Test
    fun testSearchMatch() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventSearchResponse(events)

        val eventName = "Arsenal"

        CoroutineScope(context.main).launch {
            val data = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.searchMatch(eventName)),
                        EventSearchResponse::class.java
                )
            }
            `when`(data).thenReturn(response)

            presenter.searchMatch(eventName)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun testGetLastEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        val idLeague = "4328"

        CoroutineScope(context.main).launch {
            val data = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(
                                TheSportDBApi.getLastMatch(idLeague)), EventResponse::class.java)
            }
            `when`(data).thenReturn(response)

            presenter.getLastEventList(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun testGetNextEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)

        val idLeague = "4328"

        CoroutineScope(context.main).launch {
            val data = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(
                                TheSportDBApi.getNextMatch(idLeague)), EventResponse::class.java)
            }
            `when`(data)
                    .thenReturn(response)

            presenter.getNextEventList(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }

    }
}