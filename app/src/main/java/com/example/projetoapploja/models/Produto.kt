package com.example.projetoapploja.models

data class Produto(
    val idProduto: String,
    val marca: String,
    val tipo: String,
    val genero: String,
    val novidade: String,
    val modelo: String = ""
)
