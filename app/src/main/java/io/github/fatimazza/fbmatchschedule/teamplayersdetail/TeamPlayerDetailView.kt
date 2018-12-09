package io.github.fatimazza.fbmatchschedule.teamplayersdetail

import io.github.fatimazza.fbmatchschedule.model.Players
import java.lang.RuntimeException

interface TeamPlayerDetailView {

    fun showLoading()

    fun hideLoading()

    fun showError(ex: RuntimeException)

    fun showPlayerDetail(data: List<Players>)

}