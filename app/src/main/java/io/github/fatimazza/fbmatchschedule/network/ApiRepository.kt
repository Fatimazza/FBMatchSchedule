package io.github.fatimazza.fbmatchschedule.network

import java.net.URL

class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }

}