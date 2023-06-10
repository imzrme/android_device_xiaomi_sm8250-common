/*
 * Copyright (C) 2021-2023 Miku UI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.settings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import android.os.SystemProperties;
import android.view.Display.HdrCapabilities
import android.view.SurfaceControl
import androidx.preference.PreferenceManager

import com.xiaomi.settings.display.DcUtils
import com.xiaomi.settings.fastcharge.ChargeUtils
import com.xiaomi.settings.touch.TouchUtils

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (DEBUG) Log.d(TAG, "Received boot completed intent")

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val dcDimmingEnabled = sharedPreference.getBoolean(DcUtils.DC_DIMMING_KEY, false)
        if (!DcUtils.setDcStatus(if (dcDimmingEnabled) DcUtils.DC_ON else DcUtils.DC_OFF)) {
            Log.e(TAG, "Failed to set DC node on boot!")
        }

        val highPollingRateEnabled = sharedPreference.getBoolean(TouchUtils.TOUCH_POLLING_RATE_KEY, false)
        if (!TouchUtils.setTouchStatus(if (highPollingRateEnabled) TouchUtils.HIGH_POLLING_RATE_ON else TouchUtils.HIGH_POLLING_RATE_OFF)) {
            Log.e(TAG, "Failed to set High touch poling rate node on boot!")
        }
        val fastChargeEnabled = sharedPreference.getBoolean(ChargeUtils.FAST_CHARGE_KEY, false)
        ChargeUtils.setFastChargeStatus(if (fastChargeEnabled) ChargeUtils.FAST_CHARGE_ON else ChargeUtils.FAST_CHARGE_OFF)


    companion object {
        private const val DEBUG = false
        private const val TAG = "XiaomiSettings"
    }
}