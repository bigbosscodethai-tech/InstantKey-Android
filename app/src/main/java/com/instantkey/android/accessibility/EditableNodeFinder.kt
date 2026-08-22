package com.instantkey.android.accessibility

import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

data class EditableNodeCandidate(
    val node: AccessibilityNodeInfo,
    val score: Int,
    val bounds: android.graphics.Rect,
    val reason: String
)

/**
 * EditableNodeFinder - Recursively finds the best editable input node.
 */
class EditableNodeFinder {

    fun findBestEditableNode(
        root: AccessibilityNodeInfo?,
        screenWidth: Int,
        screenHeight: Int
    ): Pair<AccessibilityNodeInfo?, Int> {
        if (root == null) return Pair(null, 0)

        var bestNode: AccessibilityNodeInfo? = null
        var bestScore = 0
        val candidates = mutableListOf<EditableNodeCandidate>()

        traverseTree(root, screenWidth, screenHeight, candidates)

        for (candidate in candidates) {
            if (candidate.score > bestScore) {
                bestScore = candidate.score
                bestNode = candidate.node
            }
        }

        Log.d(TAG, "Best editable node score: $bestScore, reason: ${bestNode?.contentDescription}")
        return Pair(bestNode, bestScore)
    }

    private fun traverseTree(
        node: AccessibilityNodeInfo?,
        screenWidth: Int,
        screenHeight: Int,
        candidates: MutableList<EditableNodeCandidate>
    ) {
        if (node == null || !isNodeValid(node)) return

        // Check if this node is a potential input field
        if (isEditableInput(node, screenWidth, screenHeight)) {
            val score = calculateScore(node, screenWidth, screenHeight)
            val bounds = android.graphics.Rect()
            node.getBoundsInScreen(bounds)
            candidates.add(
                EditableNodeCandidate(
                    node,
                    score,
                    bounds,
                    node.contentDescription?.toString() ?: "unknown"
                )
            )
        }

        // Recursively traverse children
        for (i in 0 until (node.childCount ?: 0)) {
            try {
                val child = node.getChild(i)
                traverseTree(child, screenWidth, screenHeight, candidates)
            } catch (e: Exception) {
                Log.w(TAG, "Error accessing child node: ${e.message}")
            }
        }
    }

    private fun isEditableInput(
        node: AccessibilityNodeInfo,
        screenWidth: Int,
        screenHeight: Int
    ): Boolean {
        return (node.isEditable || node.inputType != 0 ||
                node.className?.contains("EditText") == true) &&
               node.isVisibleToUser &&
               node.isEnabled
    }

    private fun calculateScore(
        node: AccessibilityNodeInfo,
        screenWidth: Int,
        screenHeight: Int
    ): Int {
        var score = 0

        // Editable
        if (node.isEditable) score += 40

        // Focusable
        if (node.isFocusable) score += 15

        // Visible
        if (node.isVisibleToUser) score += 15

        // Enabled
        if (node.isEnabled) score += 10

        // EditText-like class
        if (node.className?.contains("EditText") == true) score += 15

        // Message-related hint/description
        val hint = node.hintText?.toString()?.lowercase() ?: ""
        val desc = node.contentDescription?.toString()?.lowercase() ?: ""
        val text = node.text?.toString()?.lowercase() ?: ""
        val hints = listOf("aa", "message", "type a message", "write", "input")
        if (hints.any { hint.contains(it) || desc.contains(it) || text.contains(it) }) {
            score += 25
        }

        // Bottom region of screen
        val bounds = android.graphics.Rect()
        node.getBoundsInScreen(bounds)
        if (bounds.top > screenHeight * 0.7) score += 10

        // Clickable
        if (node.isClickable) score += 5

        return score
    }

    private fun isNodeValid(node: AccessibilityNodeInfo?): Boolean {
        return try {
            node != null && node.className != null
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private const val TAG = "InstantKey.NodeFinder"
    }
}
