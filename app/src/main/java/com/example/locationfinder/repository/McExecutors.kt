package com.example.locationfinder.repository

import androidx.annotation.MainThread
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * McExecutors
 */

class McExecutors {
    private val singleThread: ExecutorService = Executors.newSingleThreadExecutor()
    private val cachedThread: ExecutorService = Executors.newCachedThreadPool()
    private val fixedThread: ExecutorService = Executors.newFixedThreadPool(getThreadCount())

    private var coreCount: Int = 0

    private fun getThreadCount() = if (coreCount == 0) {
        Runtime.getRuntime().availableProcessors() + 1
    } else {
        THREAD_COUNT
    }

    fun single(task: Runnable) { singleThread.execute(task) }
    fun cached(task: Runnable) { cachedThread.execute(task) }
    fun fixed(task: Runnable) { fixedThread.execute(task) }

    companion object {
        const val THREAD_COUNT = 2
    }
}
