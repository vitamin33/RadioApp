package com.serbyn.radioapp.domain.dispatchers;

import kotlinx.coroutines.CoroutineDispatcher;

interface CoroutinesDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}