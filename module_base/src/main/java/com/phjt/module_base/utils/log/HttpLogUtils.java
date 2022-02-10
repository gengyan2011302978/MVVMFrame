/*
 * Copyright 2017 JessYan
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
package com.phjt.module_base.utils.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * ================================================
 * 日志工具类
 * <p>
 * ================================================
 */
public class HttpLogUtils {
    private static final String DEFAULT_TAG = "MVP";
    private static boolean isLog = true;

    private HttpLogUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean isLog) {
        HttpLogUtils.isLog = isLog;
    }

    public static void debugInfo(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void debugInfo(String msg) {
        debugInfo("MVP", msg);
    }

    public static void warnInfo(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void warnInfo(String msg) {
        warnInfo("MVP", msg);
    }

    public static void debugLongInfo(String tag, String msg) {
        if (isLog && !TextUtils.isEmpty(msg)) {
            msg = msg.trim();
            int index = 0;
            short maxLength = 3500;

            while (index < msg.length()) {
                String sub;
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, index + maxLength);
                }

                index += maxLength;
                Log.d(tag, sub.trim());
            }

        }
    }

    public static void debugLongInfo(String msg) {
        debugLongInfo("MVP", msg);
    }
}
