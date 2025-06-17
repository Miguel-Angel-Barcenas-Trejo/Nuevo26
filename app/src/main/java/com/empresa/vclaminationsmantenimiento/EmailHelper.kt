package com.empresa.vclaminationsmantenimiento

import android.content.Context
import android.widget.Toast
import java.io.File
import java.util.*
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.*
import android.util.Log
import android.app.Activity

object EmailHelper {
    //fun sendEmail(context: Context, destinatario: String, archivo: File)
    fun sendEmail(context: Context, destinatarios: List<String>, archivo: File) {
        Thread {
            try {

                val props = Properties().apply {
                    put("mail.smtp.host", "mail.vclaminations.com")
                    put("mail.smtp.port", "465")
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.socketFactory.port", "465")
                    put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                    put("mail.smtp.socketFactory.fallback", "false")
                }

                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(
                            "mantenimiento@vclaminations.com",
                            "gABqvp%mY0DN"
                        )
                    }
                })

                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress("mantenimiento@vclaminations.com"))
                    val addresses = destinatarios.map { InternetAddress(it) }.toTypedArray()
                    setRecipients(Message.RecipientType.TO, addresses)
                    //setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario))
                    subject = "Reporte de Mantenimiento"
                    setText("Adjunto encontrarás el reporte en PDF")

                    val mimeBodyPart = MimeBodyPart().apply {
                        attachFile(archivo)
                    }

                    val multipart = MimeMultipart().apply {
                        addBodyPart(mimeBodyPart)
                    }

                    setContent(multipart)
                }

                Transport.send(message)

                (context as Activity).runOnUiThread {
                    Toast.makeText(context, "Correo enviado con el PDF adjunto", Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: Exception) {
                Log.e("Correo", "Error al enviar el correo", e)
                e.printStackTrace()
                (context as Activity).runOnUiThread {
                    Toast.makeText(
                        context,
                        "Error al enviar el correo: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }
}



