package com.empresa.vclaminationsmantenimiento

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.EditText
import android.content.Intent


class LoginEmail : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Oculta el título de la barra superior
        setContentView(R.layout.activity_login_email) // Asegúrate de tener un layout XML

        auth = FirebaseAuth.getInstance() // Inicializa Firebase Authentication

        // Botón para iniciar sesión
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("Auth", "signInWithEmail:success")
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        // Redirigir al usuario a la actividad principal
                        startActivity(Intent(this, MainActivity::class.java))
                        finish() // Esto evita que el usuario regrese a LoginEmail al presionar "Atrás"

                    } else {
                        Log.w("Auth", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Usuario ya autenticado, puede ser redirigido
        }
    }
}
