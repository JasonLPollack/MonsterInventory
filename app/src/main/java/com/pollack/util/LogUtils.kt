package com.pollack.util

inline val <reified T> T.TAG get() = T::class.java.simpleName