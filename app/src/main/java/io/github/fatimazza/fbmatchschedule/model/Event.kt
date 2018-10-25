package io.github.fatimazza.fbmatchschedule.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(

        @SerializedName("dateEvent")
        var dateEvent: Date? = null,


        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: Int? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoals: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: Int? = null,


        @SerializedName("strHomeLineupGoalkeeper")
        var homeGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeSubtitues: String? = null,


        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("intAwayScore")
        var awayScore: Int? = null,

        @SerializedName("strAwayGoalDetails")
        var awayGoals: String? = null,

        @SerializedName("intAwayShots")
        var awayShots: Int? = null,


        @SerializedName("strAwayLineupGoalkeeper")
        var awayGoalkeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awaySubtitues: String? = null

)