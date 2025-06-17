package com.empresa.vclaminationsmantenimiento

data class Ticket(
    val id: String = "",
    val nombreEquipo: String = "",
    val solicitante: String = "",
    val descripcion: String = "",
    val tipoMantenimiento: String = "",
    val prioridad: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
