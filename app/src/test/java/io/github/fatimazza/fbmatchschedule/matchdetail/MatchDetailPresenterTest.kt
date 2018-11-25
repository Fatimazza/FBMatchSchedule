package io.github.fatimazza.fbmatchschedule.matchdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.TestContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailActivity

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var gson: Gson

    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val homeTeamResponse = TeamResponse(teams)
        val awayTeamResponse = TeamResponse(teams)
        val teamHomeId = "133604"
        val teamAwayId = "133616"

        GlobalScope.launch {
            `when`(gson.fromJson(
                    apiRepository.doRequest(
                            TheSportDBApi.getTeamDetail(teamHomeId)), TeamResponse::class.java))
                    .thenReturn(homeTeamResponse)

            presenter.getTeamDetail(teamHomeId, teamAwayId)

            Mockito.verify(view).showHomeTeamDetail(homeTeamResponse.teams[0])
        }

        GlobalScope.launch {
            `when`(gson.fromJson(
                    apiRepository.doRequest(
                            TheSportDBApi.getTeamDetail(teamAwayId)), TeamResponse::class.java))
                    .thenReturn(awayTeamResponse)

            Mockito.verify(view).showAwayTeamDetail(awayTeamResponse.teams[0])
        }
    }
}