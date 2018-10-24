package io.github.fatimazza.fbmatchschedule.network

import android.net.Uri
import io.github.fatimazza.fbmatchschedule.BuildConfig

object TheSportDBApi {

    fun getEvents(): String {
        return Uri.parse(BuildConfig.BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", "4328")
                .build()
                .toString()
    }

}