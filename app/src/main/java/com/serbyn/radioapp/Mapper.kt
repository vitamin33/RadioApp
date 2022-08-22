package com.serbyn.radioapp

typealias Mapper<T, R> = (T) -> R

typealias PairMapper<F, S, R> = (F, S) -> R