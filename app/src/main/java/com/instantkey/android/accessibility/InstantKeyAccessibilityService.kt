package com.instantkey.android.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.util.Log
import kotlinx.coroutines.*

/**
 * InstantKeyAccessibilityService - Main accessibility service for detecting chat screens
 * and triggering automatic keyboard display.
 */
class InstantKeyAccessibilityService : AccessibilityService() {

    private val serviceScope = CoroutineScope(Job() + Dispatchers.Main)
    private var chatDetector: ChatDetector? = null
    private var focusController: FocusController? = null
    private var keyboardLauncher: KeyboardLauncher? = null
    private var retryEngine: RetryEngine? = null
    private var lastProcessedPackage: String? = null
    private var lastScreenSignature: String? = null
    private var isProcessing = false

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "Accessibility Service Connected")
        chatDetector = ChatDetector(this)
        focusController = FocusController(this)
        keyboardLauncher = KeyboardLauncher(this)
        retryEngine = RetryEngine(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Debounce and avoid re-entry
        if (isProcessing) return
        
        val eventType = event.eventType
        val packageName = event.packageName?.toString() ?: return

        // Only process relevant event types
        if (eventType !in listOf(
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED,
            AccessibilityEvent.TYPE_WINDOWS_CHANGED,
            AccessibilityEvent.TYPE_VIEW_FOCUSED,
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
        )) return

        // Check if this is a supported app
        if (!SupportedApps.isSupportedPackage(packageName)) return

        isProcessing = true
        serviceScope.launch {
            try {
                processEvent(packageName, event)
            } finally {
                isProcessing = false
            }
        }
    }

    private suspend fun processEvent(packageName: String, event: AccessibilityEvent) {
        // Debounce repeated processing of same package
        val rootNode = rootInActiveWindow ?: return
        val screenSignature = rootNode.hashCode().toString()

        if (packageName == lastProcessedPackage && screenSignature == lastScreenSignature) {
            return
        }

        lastProcessedPackage = packageName
        lastScreenSignature = screenSignature

        // Detect if this is a chat screen
        val editableNode = chatDetector?.detectChatScreen(rootNode) ?: return
        Log.d(TAG, "Chat screen detected in $packageName")

        // Request focus on the editable node
        focusController?.requestFocus(editableNode)

        // Attempt to launch keyboard
        keyboardLauncher?.launchKeyboard()

        // Start retry engine if initial attempts fail
        retryEngine?.startRetry()
    }

    override fun onInterrupt() {
        Log.d(TAG, "Accessibility Service Interrupted")
    }

    override fun onDestroy() {
        Log.d(TAG, "Accessibility Service Destroyed")
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "InstantKey.A11y"
    }
}
