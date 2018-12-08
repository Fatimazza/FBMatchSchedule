package io.github.fatimazza.fbmatchschedule.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMatch(val id: Long?,
                         val eventId: String?,
                         val eventDate: String?,
                         val eventTime: String?,

                         val homeTeamId: String?,
                         val awayTeamId: String?,

                         val homeTeam: String?,
                         val homeScore: String?,
                         val homeGoals: String?,
                         val homeShots: String?,

                         val homeKeeper: String?,
                         val homeDefense: String?,
                         val homeMidfield: String?,
                         val homeForward: String?,
                         val homeSubtitues: String?,

                         val awayTeam: String?,
                         val awayScore: String?,
                         val awayGoals: String?,
                         val awayShots: String?,

                         val awayKeeper: String?,
                         val awayDefense: String?,
                         val awayMidfield: String?,
                         val awayForward: String?,
                         val awaySubtitues: String? ) : Parcelable

{

    companion object {

        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"

        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_TIME: String = "EVENT_TIME"

        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"

        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val HOME_GOALS: String = "HOME_GOALS"
        const val HOME_SHOTS: String = "HOME_SHOTS"

        const val HOME_KEEPER: String = "HOME_KEEPER"
        const val HOME_DEFENSE: String = "HOME_DEFENSE"
        const val HOME_MIDFIELD: String = "HOME_MIDFIELD"
        const val HOME_FORWARD: String = "HOME_FORWARD"
        const val HOME_SUBTITUES: String = "HOME_SUBTITUES"

        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val AWAY_GOALS: String = "AWAY_GOALS"
        const val AWAY_SHOTS: String = "AWAY_SHOTS"

        const val AWAY_KEEPER: String = "AWAY_KEEPER"
        const val AWAY_DEFENSE: String = "AWAY_DEFENSE"
        const val AWAY_MIDFIELD: String = "AWAY_MIDFIELD"
        const val AWAY_FORWARD: String = "AWAY_FORWARD"
        const val AWAY_SUBTITUES: String = "AWAY_SUBTITUES"

    }

}