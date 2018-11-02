package io.github.fatimazza.fbmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.id.*
import io.github.fatimazza.fbmatchschedule.favoritematch.FavoriteMatchFragment
import io.github.fatimazza.fbmatchschedule.lastmatch.LastMatchFragment
import io.github.fatimazza.fbmatchschedule.nextmatch.NextMatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener {
            item -> when(item.itemId) {
                last_match -> { loadFragment(savedInstanceState, LastMatchFragment()) }
                next_match -> { loadFragment(savedInstanceState, NextMatchFragment()) }
                favorite_match -> { loadFragment(savedInstanceState, FavoriteMatchFragment()) }
            }
            true
        }
        bottom_navigation.selectedItemId = last_match
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
