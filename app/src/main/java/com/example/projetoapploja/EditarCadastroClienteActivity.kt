package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityEditarCadastroClienteBinding
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Matcher
import java.util.regex.Pattern


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
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val inputStr: CharSequence = emailCliente
            val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher: Matcher = pattern.matcher(inputStr)
            return if (matcher.matches()) {
                true
            } else {
                false
            }
        }else{
            binding.editInputEditarEmailCliente.error = "Email não pode ser vazio."
            return false
        }
    }

    private fun verificarCadastro(opcao: String){
        var contagem = 0
        emailCliente = binding.editInputEditarEmailCliente.text.toString()
        if (verificarEmail()) {
            firestore.collection("clientes")
                .get()
                .addOnSuccessListener { result ->
                    result.forEach {
                        val emailComparacao = it.data.get("email").toString()
                        if (emailComparacao == emailCliente) {
                            val idCliente = it.data.get("userId").toString()
                            //Log.i("saida", "Email encontrado, confirma edição?")
                           // Log.i("saida", "Verifica cadastro: ${it.data.get("userId")} - $opcao")
                            alertaDialogo(idCliente,opcao)
                            return@addOnSuccessListener
                        } else {
                            contagem += 1
                        }
                    }
                    if (contagem != 0){
                        exibirMensagem("Não existe cliente cadastrado com este email.")
                    }
                }.addOnFailureListener {
                    Log.d("saida", "Error desconhecido.")
                }
        }else{
            exibirMensagem("Email e/ou formato invalido. " +
                    "\nFavor inserir um email válido." +
                    "\nEx.: maria@email.com")
        }
    }

    private fun alertaDialogo(idCliente: String, opcao: String) {
        val idUsuario = idCliente
        val novaOpcao = opcao
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(
            if (opcao == "deletar")
                {"Confirmar exclusão do usuário"
            }else {"Confirma edição do usuario."
            })
            .setMessage(
                if (opcao == "deletar")
                    {"Todos dados referentes ao usuario serão removidos."
                }else {"Você poderá editar seus dados novamente."
                })
            .setNegativeButton("Cancelar"){dialog, posicao ->
                exibirMensagem("Você cancelou a ação.")
            }.setPositiveButton(
                if (opcao == "deletar") "REMOVER" else "Editar"){dialog, posicao ->
                exibirMensagem(
                    if (opcao == "deletar")
                    {"Você removeu o usuario."
                    }else {"Você será redirecionado para edição cadastro."
                    }
                )
                editarExcluirEmail(idUsuario, novaOpcao)
            }.setIcon(R.drawable.ic_alert_24)
                .setCancelable(false)
                .create()
                .show()
    }

    private fun editarExcluirEmail(idCliente: String, opcao: String) {
        val referencia = firestore.collection("clientes").document(idCliente)
        if (opcao == "deletar") {
            referencia.delete()
            Log.i("saida", "Email excluido com sucesso.")
            startActivity(Intent(this, PrimeiraTelaActivity::class.java))
        }else if (opcao == "editar"){
            referencia.delete()
            Log.i("saida", "Redirecionando para edição.")
            startActivity(Intent(this, CadastroClienteActivity::class.java))
        }else{
            Log.i("saida","resposta nula - algo deu errado!!")
        }
    }

    private fun inicializarToolbar() {
        binding.includeToolbar.clLogo.visibility = View.GONE
        binding.includeToolbar.tbAlternativa.title = "Editar Cadastro Cliente"
        setSupportActionBar(binding.includeToolbar.tbAlternativa)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}