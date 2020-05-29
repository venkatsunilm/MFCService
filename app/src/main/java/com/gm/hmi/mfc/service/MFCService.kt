package com.gm.hmi.mfc.service

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.gm.hmi.mfc.GlobalConstants
import com.gm.hmi.mfc.SwitchAccessWindowInfo
import com.gm.hmi.mfc.nodes.MainTreeBuilder
import com.gm.hmi.mfc.util.AccessibilityServiceCompatUtils
import com.gm.hmi.mfc.util.UiChangeDetector
import com.gm.hmi.mfc.util.UiChangeStabilizer

public class MFCService : AccessibilityService() {

    private lateinit var eventProcessor: UiChangeDetector
    private lateinit var uiChangeStabilizer: UiChangeStabilizer

    init {
        Log.i(GlobalConstants.LOGTAG, "init.... ")
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(GlobalConstants.LOGTAG, "onCreate.... ")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        // get the active window information
        Log.i(GlobalConstants.LOGTAG, "onServiceConnected.... ")
        eventProcessor = UiChangeDetector()

        Handler().postDelayed({
            buildTree()
        }, 5000)

        Handler().postDelayed({ //                Log.i(GlobalConstants.LOGTAG, "currentNodeCompat_HMI_THREE Focused...");
            GlobalConstants.currentNodeCompat_previousButton.performAction(
                AccessibilityNodeInfoCompat.ACTION_FOCUS
            )
        }, 10000)
    }

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i(GlobalConstants.LOGTAG, "onAccessibilityEvent: $event")
        val source: AccessibilityNodeInfo = event?.source ?: return

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED
            && source.packageName.toString() == GlobalConstants.SYSTEM_PACKAGE_NAME
            && source.viewIdResourceName != null
        ) {
            GlobalConstants.CurrentItemIndex = event.getCurrentItemIndex()
            GlobalConstants.isFocusOnSystemAppTray = true
            Log.i(
                "Venk",
                "GlobalConstants.CurrentItemIndex : " + GlobalConstants.CurrentItemIndex
            )
        }

        if (eventProcessor != null) {
            eventProcessor.onAccessibilityEvent(event)
        }
    }

    private fun buildTree() {
        MainTreeBuilder(this).addWindowListToTree(
            SwitchAccessWindowInfo.convertZOrderWindowList(
                AccessibilityServiceCompatUtils.getWindows(this)
            )
        )
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {

        if (event!!.action == KeyEvent.ACTION_UP) {
            return false
        }

        Log.i(GlobalConstants.LOGTAG, "Switch access service: " + event.keyCode)
        val currentFocusedNode =
            AccessibilityServiceCompatUtils.getInputFocusedNode(this)
        if (currentFocusedNode != null) {
            Log.i(
                GlobalConstants.LOGTAG,
                "Service currentFocusedNode text " + currentFocusedNode.viewIdResourceName
            )
        }

        when(event.getKeyCode()){
            KeyEvent.KEYCODE_BUTTON_L1 -> {}
            KeyEvent.KEYCODE_BUTTON_R1 -> {}
        }
        return super.onKeyEvent(event)

    }

}