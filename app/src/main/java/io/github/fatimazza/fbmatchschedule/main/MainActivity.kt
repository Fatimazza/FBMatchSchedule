package io.github.fatimazza.fbmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.id.*
import io.github.fatimazza.fbmatchschedule.favoritematch.FavoriteMatchFragment
import io.github.fatimazza.fbmatchschedule.matches.MatchesFragment
import io.github.fatimazza.fbmatchschedule.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupStrictMode()

        bottom_navigation.setOnNavigationItemSelectedListener {
            item -> when(item.itemId) {
                matches -> { loadFragment(savedInstanceState, MatchesFragment()) }
                teams -> { loadFragment(savedInstanceState, TeamsFragment()) }
                favorite_match -> { loadFragment(savedInstanceState, FavoriteMatchFragment()) }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }

    private fun setupStrictMode() {
        val SDK_INT: Int = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    private fun loadFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment,
                            fragment::class.java.simpleName)
                    .commit()
        }
    }

}
