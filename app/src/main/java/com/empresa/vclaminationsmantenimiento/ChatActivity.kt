package com.empresa.vclaminationsmantenimiento

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var mensajeEditText: EditText
    private lateinit var enviarBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var mensajeAdapter: MensajeAdapter
    private lateinit var mensajeList: ArrayList<Mensaje>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mensajeEditText = findViewById(R.id.etMensaje)
        enviarBtn = findViewById(R.id.btnEnviarMensaje)
        recyclerView = findViewById(R.id.rvMensajes)

        mensajeList = arrayListOf()
        mensajeAdapter = MensajeAdapter(mensajeList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mensajeAdapter

        dbRef = FirebaseDatabase.getInstance().getReference("chats").child("grupo_unico")

        enviarBtn.setOnClickListener {
            val texto = mensajeEditText.text.toString()
            if (texto.isNotEmpty()) {
                val id = dbRef.push().key!!
                val mensaje = Mensaje(
                    id = id,
                    senderId = FirebaseAuth.getInstance().currentUser!!.uid,
                    text = texto
                )
                dbRef.child(id).setValue(mensaje)
                mensajeEditText.setText("")
            }
        }

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mensajeList.clear()
                for (snap in snapshot.children) {
                    val mensaje = snap.getValue(Mensaje::class.java)
                    mensaje?.let { mensajeList.add(it) }
                }
                mensajeAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(mensajeList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
