package com.junkyard.simplelauncher.ui.utils

import android.util.Log

object Throttle {
    private var executedAt: Long = 0

    operator fun invoke(
        delay: Long = 1_000L,
        action: () -> Unit = {}
    ) {
        Log.d("Throttle", "Time since last click: ${System.currentTimeMillis() - executedAt} ms")

        if (System.currentTimeMillis() - executedAt >= delay) {
            Log.d("Throttle", "Action executed after $delay ms delay")
            action()
            executedAt = System.currentTimeMillis()
        }
    }
}