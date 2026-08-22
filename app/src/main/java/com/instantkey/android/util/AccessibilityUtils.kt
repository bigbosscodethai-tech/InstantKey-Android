package com.instantkey.android.util

import android.content.Context
import android.provider.Settings
import android.accessibilityservice.AccessibilityManager

/**
 * AccessibilityUtils - Utilities for checking accessibility service status.
 */
object AccessibilityUtils {

    fun isAccessibilityServiceEnabled(context: Context, serviceClass: Class<*>): Boolean {
        return try {
            val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            val enabledServices = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            ) ?: ""

            val serviceComponentName = "${context.packageName}/${serviceClass.name}"
            enabledServices.contains(serviceComponentName)
        } catch (e: Exception) {
            false
        }
    }

    fun openAccessibilitySettings(context: Context) {
        val intent = android.content.Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(intent)
    }
}
