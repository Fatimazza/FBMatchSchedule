package io.github.fatimazza.fbmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Leagues(

        @SerializedName("idLeague")
        var idLeague: String? = null,

        @SerializedName("strLeague")
        var strLeague: String? = null
)