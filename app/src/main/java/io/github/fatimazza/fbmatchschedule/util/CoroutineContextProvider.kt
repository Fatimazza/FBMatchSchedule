package io.github.fatimazza.fbmatchschedule.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
    open val background: CoroutineContext by lazy { Dispatchers.IO }
}