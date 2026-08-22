package com.instantkey.android.util

import android.content.Context
import android.provider.Settings

/**
 * KeyboardUtils - Utilities for keyboard and IME management.
 */
object KeyboardUtils {

    fun isInputMethodEnabled(context: Context, imiId: String): Boolean {
        return try {
            val enabledImes = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_INPUT_METHODS
            ) ?: ""
            enabledImes.contains(imiId)
        } catch (e: Exception) {
            false
        }
    }

    fun openInputMethodSettings(context: Context) {
        val intent = android.content.Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS)
        context.startActivity(intent)
    }

    fun getInputMethodManagerServiceName(context: Context): String {
        return "${context.packageName}/.ime.InstantKeyInputMethodService"
    }
}
