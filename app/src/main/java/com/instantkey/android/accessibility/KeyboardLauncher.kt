package com.instantkey.android.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.util.Log

/**
 * KeyboardLauncher - Attempts to show the soft keyboard using various strategies.
 */
class KeyboardLauncher(private val context: AccessibilityService) {

    private val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun launchKeyboard(): Boolean {
        // Attempt A: Use SoftKeyboardController if available (API 28+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val controller = context.softKeyboardController
            if (controller != null) {
                try {
                    val result = controller.showSoftKeyboard()
                    if (result) {
                        Log.d(TAG, "Keyboard launched via SoftKeyboardController")
                        return true
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "SoftKeyboardController failed: ${e.message}")
                }
            }
        }

        // Attempt B: Try showing keyboard via InputMethodManager
        try {
            val result = inputMethodManager.showSoftInput(null, InputMethodManager.SHOW_IMPLICIT)
            if (result) {
                Log.d(TAG, "Keyboard launched via InputMethodManager")
                return true
            }
        } catch (e: Exception) {
            Log.w(TAG, "InputMethodManager showSoftInput failed: ${e.message}")
        }

        // Attempt C: Force show
        try {
            val result = inputMethodManager.showSoftInput(null, InputMethodManager.SHOW_FORCED)
            if (result) {
                Log.d(TAG, "Keyboard launched via SHOW_FORCED")
                return true
            }
        } catch (e: Exception) {
            Log.w(TAG, "InputMethodManager SHOW_FORCED failed: ${e.message}")
        }

        Log.w(TAG, "All keyboard launch attempts failed")
        return false
    }

    companion object {
        private const val TAG = "InstantKey.KbdLauncher"
    }
}
