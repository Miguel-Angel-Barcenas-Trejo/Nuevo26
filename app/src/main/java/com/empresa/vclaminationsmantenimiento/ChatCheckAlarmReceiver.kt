package com.empresa.vclaminationsmantenimiento

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.empresa.vclaminationsmantenimiento.ChatCheckerService

class ChatCheckAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ChatCheckAlarmReceiver", "Alarm received, starting service")
        val serviceIntent = Intent(context, ChatCheckerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}


