package com.mahnoosh.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: NewsDispatchers)

enum class NewsDispatchers {
    Default,
    IO,
    Main
}