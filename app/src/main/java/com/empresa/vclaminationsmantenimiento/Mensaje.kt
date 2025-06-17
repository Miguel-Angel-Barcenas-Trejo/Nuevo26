package com.empresa.vclaminationsmantenimiento

data class Mensaje(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val ticketData: Ticket? = null,
    val timestamp: Long = System.currentTimeMillis()
)
