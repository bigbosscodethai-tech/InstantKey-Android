package com.instantkey.android.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

/**
 * ChatDetector - Detects chat/conversation screens and finds editable input nodes.
 */
class ChatDetector(private val context: AccessibilityService) {
    private val editableNodeFinder = EditableNodeFinder()

    fun detectChatScreen(rootNode: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (rootNode == null) return null

        // Search for editable text input in the accessibility tree
        val (editableNode, score) = editableNodeFinder.findBestEditableNode(
            rootNode,
            context.resources.displayMetrics.widthPixels,
            context.resources.displayMetrics.heightPixels
        )

        Log.d(TAG, "Chat detection score: $score")
        return if (score >= MINIMUM_CONFIDENCE_SCORE) editableNode else null
    }

    companion object {
        private const val TAG = "InstantKey.ChatDetector"
        private const val MINIMUM_CONFIDENCE_SCORE = 50
    }
}
