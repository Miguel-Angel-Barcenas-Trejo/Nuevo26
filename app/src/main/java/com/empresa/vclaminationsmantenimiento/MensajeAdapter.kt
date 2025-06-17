package com.empresa.vclaminationsmantenimiento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MensajeAdapter(private val mensajes: List<Mensaje>) :
    RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    // Mapa estático de UID a nombre real
    private val uidToNombre = mapOf(
        "ytkWdXa1CfVpG5ya3FQRfmVXy0t1" to "Eduardo Mendoza",
        "nStpDMq3icMaS2F76DzQiXdqMKw2" to "Miguel Barcenas",
        "ohUds5IIkVhRiXquOWL65ZLNgF92" to "Miguel Trejo",
        "xj74C74rgnVVe0RqxxyxzUOg1dJ2" to "Supervisores",
        "Gk1R5vfjk7XidfXD2eDmrtiXexK2" to "Irvin Cano"

        // Agrega aquí los UID reales que ves en Firebase Authentication
    )

    class MensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRemitente: TextView = itemView.findViewById(R.id.tvRemitente)
        val tvMensaje: TextView = itemView.findViewById(R.id.tvMensaje)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mensaje, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]

        // Muestra el nombre si está en el mapa, si no muestra el UID
        val nombre = uidToNombre[mensaje.senderId] ?: mensaje.senderId
        holder.tvRemitente.text = nombre
        holder.tvMensaje.text = mensaje.text
    }

    override fun getItemCount(): Int = mensajes.size
}


