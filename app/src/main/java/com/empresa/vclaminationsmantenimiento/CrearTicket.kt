package com.empresa.vclaminationsmantenimiento

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CrearTicket : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etSolicitante: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var spinnerMantenimiento: Spinner
    private lateinit var spinnerPrioridad: Spinner
    private lateinit var btnEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_ticket) // Asegúrate que este es tu XML

        // Referencias a los campos
        etNombre = findViewById(R.id.etNombre)
        etSolicitante = findViewById(R.id.etSolicitante)
        etDescripcion = findViewById(R.id.etDescripcion)
        spinnerMantenimiento = findViewById(R.id.spinnerMantenimiento)
        spinnerPrioridad = findViewById(R.id.spinnerPrioridad)
        btnEnviar = findViewById(R.id.btnEnviar)

        // Opciones para los spinners
        val tiposMantenimiento = listOf("Preventivo", "Correctivo")
        val nivelesPrioridad = listOf("Urgente", "Alta", "Media", "Baja")

        spinnerMantenimiento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposMantenimiento).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinnerPrioridad.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nivelesPrioridad).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Lógica al presionar el botón Enviar
        btnEnviar.setOnClickListener {
            val ticket = Ticket(
                id = FirebaseDatabase.getInstance().reference.push().key!!,
                nombreEquipo = etNombre.text.toString(),
                solicitante = etSolicitante.text.toString(),
                descripcion = etDescripcion.text.toString(),
                tipoMantenimiento = spinnerMantenimiento.selectedItem.toString(),
                prioridad = spinnerPrioridad.selectedItem.toString()
            )

            val dbRef = FirebaseDatabase.getInstance().getReference("chats").child("grupo_unico")
            val mensaje = Mensaje(
                id = dbRef.push().key!!,
                senderId = FirebaseAuth.getInstance().currentUser!!.uid,
                text = "🎫 Nuevo ticket creado:\n" +
                        "Equipo: ${ticket.nombreEquipo}\n" +
                        "Solicitante: ${ticket.solicitante}\n" +
                        "Descripción: ${ticket.descripcion}\n" +
                        "Mantenimiento: ${ticket.tipoMantenimiento}\n" +
                        "Prioridad: ${ticket.prioridad}",
                ticketData = ticket
            )

            dbRef.child(mensaje.id).setValue(mensaje).addOnSuccessListener {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al enviar ticket", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

