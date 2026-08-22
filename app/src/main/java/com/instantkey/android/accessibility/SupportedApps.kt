package com.instantkey.android.accessibility

import android.content.Context
import android.util.Log

/**
 * SupportedApps - Manages list of supported applications.
 */
object SupportedApps {
    private const val TAG = "InstantKey.SupportedApps"

    // Package names of supported apps
    private val supportedPackages = setOf(
        "com.facebook.orca",           // Messenger
        "com.whatsapp",                 // WhatsApp
        "org.telegram.messenger",       // Telegram
        "jp.naver.line.android",        // LINE
        "com.instagram.android",        // Instagram
        "com.google.android.apps.messaging" // Google Messages
    )

    fun isSupportedPackage(packageName: String): Boolean {
        return supportedPackages.contains(packageName)
    }

    fun getSupportedPackages(): Set<String> {
        return supportedPackages.toSet()
    }

    fun getAppName(packageName: String): String {
        return when (packageName) {
            "com.facebook.orca" -> "Messenger"
            "com.whatsapp" -> "WhatsApp"
            "org.telegram.messenger" -> "Telegram"
            "jp.naver.line.android" -> "LINE"
            "com.instagram.android" -> "Instagram"
            "com.google.android.apps.messaging" -> "Google Messages"
            else -> "Unknown"
        }
    }
}
