package com.instantkey.android

import android.app.Application
import android.util.Log

/**
 * InstantKey Application - Initializes application-level components.
 */
class InstantKeyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "InstantKey Application initialized")
    }

    companion object {
        const val TAG = "InstantKey"
    }
}
