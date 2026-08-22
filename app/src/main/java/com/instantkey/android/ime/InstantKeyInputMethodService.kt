package com.instantkey.android.ime

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.util.Log

/**
 * InstantKeyInputMethodService - Custom keyboard IME.
 */
class InstantKeyInputMethodService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private var lastEditorInfo: EditorInfo? = null

    override fun onCreateInputView(): android.view.View {
        Log.d(TAG, "Creating input view")
        keyboardView = KeyboardView(this)
        keyboard = Keyboard(this, android.R.xml.qwerty)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        lastEditorInfo = attribute
        Log.d(TAG, "Input started: ${attribute?.inputType}")
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        Log.d(TAG, "Key pressed: $primaryCode")
        val ic = currentInputConnection ?: return

        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                ic.deleteSurroundingText(1, 0)
            }
            Keyboard.KEYCODE_SHIFT -> {
                keyboard.setShifted(!keyboard.isShifted)
                keyboardView.invalidateAllKeys()
            }
            32 -> { // Space
                ic.commitText(" ", 1)
            }
            10 -> { // Enter
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            }
            else -> {
                if (primaryCode > 0) {
                    val c = primaryCode.toChar().toString()
                    ic.commitText(c, 1)
                }
            }
        }
    }

    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}
    override fun onText(text: CharSequence?) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}

    companion object {
        private const val TAG = "InstantKey.IME"
    }
}
