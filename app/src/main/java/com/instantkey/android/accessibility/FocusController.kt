package com.instantkey.android.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log
import kotlinx.coroutines.*

/**
 * FocusController - Manages focus requests on editable nodes.
 */
class FocusController(private val context: AccessibilityService) {

    fun requestFocus(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false

        return try {
            // Step 1: Verify node
            if (!isNodeValid(node)) return false

            // Step 2-4: Request focus
            node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
            Log.d(TAG, "Focus requested on node")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting focus: ${e.message}")
            false
        }
    }

    fun clickAndFocus(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false

        return try {
            // First try focus
            if (node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)) {
                Log.d(TAG, "Focus successful on first attempt")
                return true
            }

            // If focus failed, try click
            if (node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.d(TAG, "Click performed on node")
                // Delay and try focus again
                Thread.sleep(100)
                node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                Log.d(TAG, "Focus requested after click")
                return true
            }

            false
        } catch (e: Exception) {
            Log.e(TAG, "Error in click-and-focus: ${e.message}")
            false
        }
    }

    private fun isNodeValid(node: AccessibilityNodeInfo?): Boolean {
        return try {
            node != null && node.isEnabled && node.isVisibleToUser
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private const val TAG = "InstantKey.FocusCtrl"
    }
}
