package io.github.fatimazza.fbmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Players(

        @SerializedName("idPlayer")
        var idPlayer: String? = null,

        @SerializedName("strPlayer")
        var playerName: String? = null,

        @SerializedName("dateBorn")
        var playerDOB: String? = null,

        @SerializedName("strDescriptionEN")
        var playerDesc: String? = null,

        @SerializedName("strPosition")
        var playerPosition: String? = null,

        @SerializedName("strFanart1")
        var playerFanart: String? = null,

        @SerializedName("strThumb")
        var playerThumb: String? = null,

        @SerializedName("strHeight")
        var playerHeight: String? = null,

        @SerializedName("strWeight")
        var playerWeight: String? = null

)