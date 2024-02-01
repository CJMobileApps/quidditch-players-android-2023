package com.cjmobileapps.quidditchplayersandroid.util.coroutine

import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {
    val io: CoroutineContext
    val ioExecutor: Executor
    val main: CoroutineContext
}
