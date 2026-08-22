package com.instantkey.android.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "instantkey_settings")

/**
 * AppSettings - Manages application preferences using DataStore.
 */
class AppSettings(private val context: Context) {

    companion object {
        private val AUTO_KEYBOARD_ENABLED = booleanPreferencesKey("auto_keyboard_enabled")
        private val MESSENGER_ENABLED = booleanPreferencesKey("messenger_enabled")
        private val WHATSAPP_ENABLED = booleanPreferencesKey("whatsapp_enabled")
        private val TELEGRAM_ENABLED = booleanPreferencesKey("telegram_enabled")
        private val LINE_ENABLED = booleanPreferencesKey("line_enabled")
        private val INSTAGRAM_ENABLED = booleanPreferencesKey("instagram_enabled")
        private val MESSAGES_ENABLED = booleanPreferencesKey("messages_enabled")
        private val DELAY_MS = intPreferencesKey("delay_ms")
        private val RETRY_COUNT = intPreferencesKey("retry_count")
        private val RETRY_INTERVAL_MS = intPreferencesKey("retry_interval_ms")
        private val CLICK_FALLBACK_ENABLED = booleanPreferencesKey("click_fallback_enabled")
        private val FOCUS_FALLBACK_ENABLED = booleanPreferencesKey("focus_fallback_enabled")
        private val DEBUG_LOGGING_ENABLED = booleanPreferencesKey("debug_logging_enabled")
    }

    val autoKeyboardEnabled: Flow<Boolean> = context.dataStore.data.map { it[AUTO_KEYBOARD_ENABLED] ?: true }
    val messengerEnabled: Flow<Boolean> = context.dataStore.data.map { it[MESSENGER_ENABLED] ?: true }
    val whatsappEnabled: Flow<Boolean> = context.dataStore.data.map { it[WHATSAPP_ENABLED] ?: true }
    val telegramEnabled: Flow<Boolean> = context.dataStore.data.map { it[TELEGRAM_ENABLED] ?: true }
    val lineEnabled: Flow<Boolean> = context.dataStore.data.map { it[LINE_ENABLED] ?: true }
    val instagramEnabled: Flow<Boolean> = context.dataStore.data.map { it[INSTAGRAM_ENABLED] ?: true }
    val messagesEnabled: Flow<Boolean> = context.dataStore.data.map { it[MESSAGES_ENABLED] ?: true }
    val delayMs: Flow<Int> = context.dataStore.data.map { it[DELAY_MS] ?: 100 }
    val retryCount: Flow<Int> = context.dataStore.data.map { it[RETRY_COUNT] ?: 3 }
    val retryIntervalMs: Flow<Int> = context.dataStore.data.map { it[RETRY_INTERVAL_MS] ?: 100 }
    val clickFallbackEnabled: Flow<Boolean> = context.dataStore.data.map { it[CLICK_FALLBACK_ENABLED] ?: true }
    val focusFallbackEnabled: Flow<Boolean> = context.dataStore.data.map { it[FOCUS_FALLBACK_ENABLED] ?: true }
    val debugLoggingEnabled: Flow<Boolean> = context.dataStore.data.map { it[DEBUG_LOGGING_ENABLED] ?: false }

    suspend fun setAutoKeyboardEnabled(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_KEYBOARD_ENABLED] = enabled }
    }

    suspend fun setMessengerEnabled(enabled: Boolean) {
        context.dataStore.edit { it[MESSENGER_ENABLED] = enabled }
    }

    suspend fun setWhatsappEnabled(enabled: Boolean) {
        context.dataStore.edit { it[WHATSAPP_ENABLED] = enabled }
    }

    suspend fun setTelegramEnabled(enabled: Boolean) {
        context.dataStore.edit { it[TELEGRAM_ENABLED] = enabled }
    }

    suspend fun setLineEnabled(enabled: Boolean) {
        context.dataStore.edit { it[LINE_ENABLED] = enabled }
    }

    suspend fun setInstagramEnabled(enabled: Boolean) {
        context.dataStore.edit { it[INSTAGRAM_ENABLED] = enabled }
    }

    suspend fun setMessagesEnabled(enabled: Boolean) {
        context.dataStore.edit { it[MESSAGES_ENABLED] = enabled }
    }

    suspend fun setDelayMs(delayMs: Int) {
        context.dataStore.edit { it[DELAY_MS] = delayMs }
    }

    suspend fun setRetryCount(count: Int) {
        context.dataStore.edit { it[RETRY_COUNT] = count }
    }

    suspend fun setRetryIntervalMs(intervalMs: Int) {
        context.dataStore.edit { it[RETRY_INTERVAL_MS] = intervalMs }
    }

    suspend fun setClickFallbackEnabled(enabled: Boolean) {
        context.dataStore.edit { it[CLICK_FALLBACK_ENABLED] = enabled }
    }

    suspend fun setFocusFallbackEnabled(enabled: Boolean) {
        context.dataStore.edit { it[FOCUS_FALLBACK_ENABLED] = enabled }
    }

    suspend fun setDebugLoggingEnabled(enabled: Boolean) {
        context.dataStore.edit { it[DEBUG_LOGGING_ENABLED] = enabled }
    }
}
