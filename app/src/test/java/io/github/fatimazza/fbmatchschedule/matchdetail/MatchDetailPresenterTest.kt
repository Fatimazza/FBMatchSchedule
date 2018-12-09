package io.github.fatimazza.fbmatchschedule.matchdetail

import com.google.gson.Gson
import io.github.fatimazza.fbmatchschedule.model.Team
import io.github.fatimazza.fbmatchschedule.model.TeamResponse
import io.github.fatimazza.fbmatchschedule.network.ApiRepository
import io.github.fatimazza.fbmatchschedule.network.TheSportDBApi
import io.github.fatimazza.fbmatchschedule.util.TestContextProvider
import kotlinx.coroutines.*
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

    @Mock
    private
    lateinit var context: TestContextProvider

    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        context = TestContextProvider()
        presenter = MatchDetailPresenter(view, apiRepository, gson, context)
    }

    @Test
    fun testGetTeamDetail() {
        var teams: MutableList<Team> = mutableListOf()
        val homeTeamResponse = TeamResponse(teams)
        val awayTeamResponse = TeamResponse(teams)
        val teamHomeId = "133604"
        val teamAwayId = "133616"

        CoroutineScope(context.main).launch {
            val requestHome = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(
                                TheSportDBApi.getTeamDetail(teamHomeId)), TeamResponse::class.java)
            }
            `when`(requestHome)
                    .thenReturn(homeTeamResponse)

            presenter.getTeamDetail(teamHomeId, teamAwayId)

            Mockito.verify(view).showHomeTeamDetail(homeTeamResponse.teams[0])
        }

        CoroutineScope(context.main).launch {
            val requestAway = withContext(context.background) {
                gson.fromJson(
                        apiRepository.doRequest(
                                TheSportDBApi.getTeamDetail(teamAwayId)), TeamResponse::class.java)
            }
            `when`(requestAway)
                    .thenReturn(awayTeamResponse)

            presenter.getTeamDetail(teamHomeId, teamAwayId)

            Mockito.verify(view).showAwayTeamDetail(awayTeamResponse.teams[0])

        }

    }
}