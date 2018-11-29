package io.github.fatimazza.fbmatchschedule.teamplayers

import io.github.fatimazza.fbmatchschedule.model.Players

interface TeamPlayersView {

    fun showLoading()

    fun hideLoading()

    fun showPlayerList(data: List<Players>)

}