package io.github.fatimazza.fbmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Team(

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
)