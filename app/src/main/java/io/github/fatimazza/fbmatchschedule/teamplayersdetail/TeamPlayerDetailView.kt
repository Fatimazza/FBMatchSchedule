package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import io.github.fatimazza.fbmatchschedule.model.Players

interface TeamPlayerDetailView {

    fun showLoading()

    fun hideLoading()

    fun showPlayerDetail(data: List<Players>)

}