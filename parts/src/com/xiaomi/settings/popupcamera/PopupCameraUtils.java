/*
 * Copyright (C) 2019 The LineageOS Project
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

package com.xiaomi.settings.popupcamera;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;

public class PopupCameraUtils {

    private static final String TAG = "PopupCameraUtils";
    private static final boolean DEBUG = false;

    private static final boolean isPopUpMotorAvailable() {
        return PopupCameraService.getMotorService() != null;
    }

    protected static void startService(Context context) {
        if (DEBUG) Log.d(TAG, "Starting service");
        context.startServiceAsUser(new Intent(context, PopupCameraService.class),
                UserHandle.CURRENT);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(context, PopupCameraSettingsActivity.class),
                pm.COMPONENT_ENABLED_STATE_ENABLED, pm.SYNCHRONOUS);
    }

    protected static void stopService(Context context) {
        if (DEBUG) Log.d(TAG, "Stopping service");
        context.stopServiceAsUser(new Intent(context, PopupCameraService.class),
                UserHandle.CURRENT);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(context, PopupCameraSettingsActivity.class),
                pm.COMPONENT_ENABLED_STATE_DEFAULT, pm.SYNCHRONOUS);
    }

    public static void checkPopupCameraService(Context context) {
        if (isPopUpMotorAvailable()) {
            startService(context);
        } else {
            stopService(context);
        }
    }
}
