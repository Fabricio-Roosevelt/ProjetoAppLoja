package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.projetoapploja.databinding.ActivityEditarCadastroClienteBinding
import com.google.firebase.firestore.FirebaseFirestore


class EditarCadastroClienteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditarCadastroClienteBinding.inflate(layoutInflater)
    }
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private lateinit var emailCliente: String
    private lateinit var opcao: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializarToolbar()
        eventosClique()
    }

    private fun eventosClique() {
        binding.btnEditarClienteConfirmar.setOnClickListener {
            opcao = "editar"
            verificarCadastro(opcao)
        }
        binding.btnEditarClienteExluir.setOnClickListener {
            opcao = "deletar"
            verificarCadastro(opcao)
        }
        binding.btnEditarClienteCancelar.setOnClickListener {
            binding.editInputEditarEmailCliente.text = null
        }
    }

    private fun verificarEmail() : Boolean{
        emailCliente = binding.editInputEditarEmailCliente.text.toString()
        if (emailCliente.isNotEmpty()){
            binding.editInputEditarEmailCliente.error = null
            return true
        }else{
            binding.editInputEditarEmailCliente.error = "Nome não pode ser vazio."
            return false
        }
    }

    private fun verificarCadastro(opcao: String){
        if (verificarEmail()) {
            emailCliente = binding.editInputEditarEmailCliente.text.toString()
            firestore.collection("clientes")
                .get()
                .addOnSuccessListener { result ->
                    result.forEach {
                        val emailComparacao = it.data.get("email").toString()
                        if (emailComparacao == emailCliente) {
                            val idCliente = it.data.get("userId").toString()
                            // Inserir popup alerta aqui
                            Log.i("saida", "Email encontrado, confirma edição?")
                            Log.i("saida", "${it.data.get("userId")}")
                            if (opcao == "editar"){
                                editarEmail(idCliente)
                            }else if (opcao == "deletar"){
                                excluirEmail(idCliente)
                            }
                            return@addOnSuccessListener
                        } else {
                            Log.i("saida", "Email não cadastrado na base de dados.")
                        }
                    }
                }.addOnFailureListener {
                    Log.d("saida", "Error desconhecido.")
                }
        }
    }

    private fun excluirEmail(idCliente: String) {
        val referencia = firestore.collection("clientes").document(idCliente).delete()
        Log.i("saida","Email excluido com sucesso.")
        startActivity(Intent(this, PrimeiraTelaActivity::class.java))
    }

    private fun editarEmail(idCliente: String) {
        val referencia = firestore.collection("clientes").document(idCliente).delete()
        Log.i("saida","Email excluido com sucesso.")
        startActivity(Intent(this, CadastroClienteActivity::class.java))
    }

    private fun inicializarToolbar() {
        binding.includeToolbar.clLogo.visibility = View.GONE
        binding.includeToolbar.tbAlternativa.title = "Editar Cadastro Cliente"
        setSupportActionBar(binding.includeToolbar.tbAlternativa)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}