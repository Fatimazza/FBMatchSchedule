package io.github.fatimazza.fbmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context)
    : ManagedSQLiteOpenHelper(ctx, "FavoriteEvents.db", null, 1) {

    companion object {

        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
        
    }

    override fun onCreate(db: SQLiteDatabase) {
        // create Tables
        db.createTable(FavoriteMatch.TABLE_FAVORITE, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
                FavoriteMatch.EVENT_DATE to TEXT,
                FavoriteMatch.EVENT_TIME to TEXT,

                FavoriteMatch.HOME_TEAM_ID to TEXT,
                FavoriteMatch.AWAY_TEAM_ID to TEXT,

                FavoriteMatch.HOME_TEAM to TEXT,
                FavoriteMatch.HOME_SCORE to TEXT,
                FavoriteMatch.HOME_GOALS to TEXT,
                FavoriteMatch.HOME_SHOTS to TEXT,

                FavoriteMatch.HOME_KEEPER to TEXT,
                FavoriteMatch.HOME_DEFENSE to TEXT,
                FavoriteMatch.HOME_MIDFIELD to TEXT,
                FavoriteMatch.HOME_FORWARD to TEXT,
                FavoriteMatch.HOME_SUBTITUES to TEXT,

                FavoriteMatch.AWAY_TEAM to TEXT,
                FavoriteMatch.AWAY_SCORE to TEXT,
                FavoriteMatch.AWAY_GOALS to TEXT,
                FavoriteMatch.AWAY_SHOTS to TEXT,

                FavoriteMatch.AWAY_KEEPER to TEXT,
                FavoriteMatch.AWAY_DEFENSE to TEXT,
                FavoriteMatch.AWAY_MIDFIELD to TEXT,
                FavoriteMatch.AWAY_FORWARD to TEXT,
                FavoriteMatch.AWAY_SUBTITUES to TEXT)

        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // upgrade Tables
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }

}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)

