package io.github.fatimazza.fbmatchschedule.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val background: CoroutineContext = Dispatchers.Unconfined
}