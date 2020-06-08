package com.alancamargo.knockerupper.framework.helpers.crashreport

import android.util.Log
import com.alancamargo.knockerupper.data.helpers.crashreport.CrashReportManager
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun log(message: String) {
        Log.d(TAG, message)
        FirebaseCrashlytics.getInstance().log(message)
    }

    override fun log(t: Throwable) {
        Log.e(TAG, t.message, t)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

    private companion object {
        const val TAG = "LOG_ALAN"
    }

}