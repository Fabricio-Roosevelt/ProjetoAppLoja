package com.example.projetoapploja

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityCadastroClienteBinding
import com.example.projetoapploja.models.Cliente
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


class CadastroClienteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroClienteBinding.inflate(layoutInflater)
    }
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }
    var dadosCliente = mutableListOf<String>()
    private lateinit var nomeCliente: String
    private lateinit var emailCliente: String
    private lateinit var telefoneCliente: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        switchNotificacoes()
        eventosClique()

    }

    private fun eventosClique() {
        binding.btnCadastrarCliente.setOnClickListener {
            nomeCliente = binding.editTextNomeCliente.text.toString()
            emailCliente = binding.editTextEmailCliente.text.toString()

            verificarNome()
            verificarEmail()
            listaWhatsApp()
            //incluiTelefone()
            listaQuerReceberNovidades()
            verificarDuplicidadeEmail()
            //confirmaInclusaoCliente()

        }
    }

    private fun listaQuerReceberNovidades() : Boolean {
        if (binding.switchReceberEmail.isChecked){
            return true
        }
        return false
    }

    private fun verificarDuplicidadeEmail() {
        var emailsIguais = 0
        firestore.collection("clientes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val emailComparacao = document.data.get("email").toString()
                    if (emailComparacao == emailCliente) {
                        emailsIguais += 1
                    }
                }
                if (emailsIguais == 0){
                    Log.i("texte","OK, email: $emailCliente adicionado com sucesso.")
                    confirmaInclusaoCliente()
                }else{
                    Log.i("saida","Email ${emailCliente} já cadastrado, tente outro.")
                    exibirMensagem("Email ${emailCliente} já cadastrado, tente outro.")
                }
            }.addOnFailureListener {
                Log.d("texte", "Error getting documents: ")
            }
    }

    fun confirmaInclusaoCliente() {
        val referencia = firestore.collection("clientes").document()
        val idUsuario = referencia.id
        val recerNovidadeEmail = listaQuerReceberNovidades()
        val reberWhatsApp = listaWhatsApp()

        val cliente = Cliente(
            idUsuario,
            nomeCliente,
            emailCliente,
            incluiTelefone(telefoneCliente),
            recerNovidadeEmail,
            reberWhatsApp
        )
        if (verificarEmail() && verificarNome()){
            firestore
                .collection( "clientes")
                .document(idUsuario)
                .set(cliente)
                .addOnSuccessListener {
                    exibirMensagem("Cliente cadastrado com sucesso!")
                    Log.i("saida","${cliente.nome} ")
                    //startActivity(Intent(applicationContext, LoginActivity::class.java))
                }.addOnFailureListener {
                    exibirMensagem("Erro ao fazer seu cadastro.")
                }
        }
    }

    private fun rascunho() : String{

        // rascunho
        val referencia = firestore.collection("clientes").document()
        val saida = referencia.get().addOnCompleteListener { document ->
            document.result.id
            val olhar = document.result.data?.get("email")
            Log.i("saida","Aqui --- ${document.result.id}.")
            Log.i("saida","Alternativa --- ${olhar}.")
            Log.i("saida","Referencia --- .")
        }.addOnFailureListener {
            Log.i("saida","Erro ao ayualizar Id.")
        }
        return saida.toString()

                   // val idUser = document.data.get("userId").toString()
                   // val novoId = document.id

    }

    private fun verificarEmail() : Boolean{
        emailCliente = binding.editTextEmailCliente.text.toString()
        if (emailCliente.isNotEmpty()){
            binding.editTextEmailCliente.error = null
            return true
        }else{
            binding.editTextEmailCliente.error = "Nome não pode ser vazio."
            return false
        }
    }

    private fun verificarNome() : Boolean{
        nomeCliente = binding.editTextNomeCliente.text.toString()
        if (nomeCliente.isNotEmpty()){
            binding.textInputNomeCliente.error = null
            return true
        }else{
            binding.textInputNomeCliente.error = "Nome não pode ser vazio."
            return false
        }
    }

    fun incluiTelefone(numeroTelefone:String) : String {
        //telefoneCliente = binding.editInputTelefoneCliente.text.toString()
        if (numeroTelefone.isNotEmpty()){
            try {
                val formattedNumber = PhoneNumberUtils.formatNumberToE164(numeroTelefone, "BR")
                Log.i("saida","numero: $formattedNumber")
                return  formattedNumber
            }catch (e: Exception){
                Toast.makeText(this, "Numero invalido. \nDigite com o DDD (Ex: 84) e sem espaço entre os números.", Toast.LENGTH_SHORT).show()
            }
        }
        return "55"


        //rascunho
        /*if (telefoneCliente.isEmpty()){
           Toast.makeText(this, "Digite seu telefone com ddd.", Toast.LENGTH_LONG).show()
       }
       try {
           val formattedNumber = PhoneNumberUtils.formatNumberToE164(telefoneCliente, "BR")
           Log.i("saida","numero: $formattedNumber")
           return  formattedNumber
       }catch (e: Exception){
           Toast.makeText(this, "Numero invalido. \nDigite com o DDD (Ex: 84) e sem espaço entre os números.", Toast.LENGTH_SHORT).show()
       }*/

    }

    private fun listaWhatsApp() : Boolean{
        if (binding.switchReceberMensagem.isChecked){
            telefoneCliente = binding.editInputTelefoneCliente.text.toString()
            incluiTelefone(telefoneCliente)
            return  true
        }
        return false
    }

    private fun switchNotificacoes() {
        binding.switchReceberMensagem.setOnClickListener {
            binding.textNotificacaoMensagem.visibility =
                if (binding.switchReceberMensagem.isChecked) View.VISIBLE
            else View.INVISIBLE
            binding.textInputTelefoneCliente.visibility =
                if (binding.switchReceberMensagem.isChecked) View.VISIBLE
                else View.INVISIBLE
        }
    }




}