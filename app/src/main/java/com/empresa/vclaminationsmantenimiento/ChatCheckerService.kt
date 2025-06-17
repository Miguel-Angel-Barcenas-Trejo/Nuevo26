package com.empresa.vclaminationsmantenimiento

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.google.firebase.database.*
import com.empresa.vclaminationsmantenimiento.NotificationUtils
import androidx.core.app.NotificationCompat

class ChatCheckerService : Service() {

    private lateinit var dbRef: DatabaseReference
    private var lastMessageId: String? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundServiceWithNotification()
        dbRef = FirebaseDatabase.getInstance().getReference("chats").child("grupo_unico")

        dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lastMessageId = snapshot.children.firstOrNull()?.key
                Log.d("ChatCheckerService", "Last message ID initialized: $lastMessageId")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ChatCheckerService", "Firebase error: ${error.message}")
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ChatCheckerService", "Service started, checking for messages")
        checkForNewMessages()
        // No detengas el servicio inmediatamente; si quieres detenerlo, hazlo luego de la comprobación
        // Puedes usar un handler para detenerlo después de un delay si es necesario
        return START_STICKY  // o START_NOT_STICKY si quieres que no se reinicie automáticamente
    }

    private fun checkForNewMessages() {
        dbRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMsg = snapshot.children.firstOrNull()
                val newId = newMsg?.key
                if (newId != null && newId != lastMessageId) {
                    lastMessageId = newId
                    val text = newMsg.child("text").getValue(String::class.java) ?: "Nuevo mensaje"
                    Log.d("ChatCheckerService", "Nuevo mensaje detectado: $text")
                    NotificationUtils.sendNotification(applicationContext, "Nuevo mensaje", text)
                } else {
                    Log.d("ChatCheckerService", "No hay nuevos mensajes")
                }
                // Si quieres detener el servicio después de la comprobación, hazlo aquí:
                // stopSelf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ChatCheckerService", "Firebase error: ${error.message}")
            }
        })
    }

    private fun startForegroundServiceWithNotification() {
        val channelId = "chat_checker_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId, "Chat Checker", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(chan)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Revisando nuevos mensajes...")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Ícono válido obligatorio
            .setOngoing(true) // Para que la notificación no se pueda deslizar
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

