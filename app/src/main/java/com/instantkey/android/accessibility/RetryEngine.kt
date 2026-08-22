package com.instantkey.android.accessibility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import kotlinx.coroutines.*

/**
 * RetryEngine - Manages controlled retry logic with cancellation.
 */
class RetryEngine(private val context: AccessibilityService) {

    private var retryJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    fun startRetry(maxRetries: Int = 3, intervalMs: Long = 100) {
        // Cancel any existing retry
        cancelRetry()

        retryJob = scope.launch {
            for (attempt in 1..maxRetries) {
                Log.d(TAG, "Retry attempt $attempt of $maxRetries")
                delay(intervalMs)

                // Check if still relevant (service still active, etc.)
                if (!isActive) {
                    Log.d(TAG, "Retry cancelled - job not active")
                    break
                }

                Log.d(TAG, "Retry $attempt completed")
            }
            Log.d(TAG, "Retry sequence finished")
        }
    }

    fun cancelRetry() {
        retryJob?.cancel()
        retryJob = null
        Log.d(TAG, "Retry cancelled")
    }

    fun cleanup() {
        cancelRetry()
        scope.cancel()
    }

    companion object {
        private const val TAG = "InstantKey.RetryEngine"
    }
}
