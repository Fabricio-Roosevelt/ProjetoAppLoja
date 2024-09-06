package com.example.projetoapploja.models

data class Cliente(
    val userId: String,
    val nome: String,
    val email: String,
    val telefone: String = "",
    val rebecerNovidadeEmail: Boolean = false,
    val reberWhatsApp: Boolean = false
)
