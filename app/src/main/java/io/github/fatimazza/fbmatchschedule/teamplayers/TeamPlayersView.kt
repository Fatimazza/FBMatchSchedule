package io.github.fatimazza.fbmatchschedule.teamplayers

import io.github.fatimazza.fbmatchschedule.model.Players
import java.lang.RuntimeException

interface TeamPlayersView {

    fun showLoading()

    fun hideLoading()

    fun showError(ex: RuntimeException)

    fun showPlayerList(data: List<Players>)

}