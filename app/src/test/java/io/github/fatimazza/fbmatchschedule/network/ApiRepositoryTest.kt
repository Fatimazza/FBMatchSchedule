package io.github.fatimazza.fbmatchschedule.network

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ApiRepositoryTest {

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Test
    fun testDoRequest() {
        apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=German%20Bundesliga"

        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}