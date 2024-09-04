package com.example.projetoapploja.utils

import android.app.Activity
import android.widget.Toast
import com.example.projetoapploja.Mensagem

fun Activity.exibirMensagem(mensagem: String){
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_LONG
        ).show()
}

fun Activity.verificarUsuario(email: String, senha: String) : Boolean{
    val administrador = "fabricioroosevelt@gmail.com"
    val superUsuario = "galdinomariza@gmail.com"
    val superSenha = "170177@041281"
    if (email == administrador || email == superUsuario && senha == superSenha){
        return true
    }
    else{
        return false
    }
}