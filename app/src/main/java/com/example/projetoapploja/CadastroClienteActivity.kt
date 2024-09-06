package com.example.projetoapploja

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityCadastroClienteBinding
import com.example.projetoapploja.models.Cliente
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


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
            incluiTelefone()
            verificarDuplicidadeEmail()
            //confirmaInclusaoCliente()
            //imprimirCliente()


            /*
            if (nomeCliente.isNotEmpty() && emailCliente.isNotEmpty()) {
                dadosCliente.add(nomeCliente)
                dadosCliente.add(emailCliente)
                if (telefoneCliente.isNotEmpty()){
                    dadosCliente.add(telefoneCliente)
                }
            }else {
                Toast.makeText(this, "Os campos %S(nome) e email são obrigatorios!", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, "$dadosCliente", Toast.LENGTH_SHORT).show()
            dadosCliente.clear()*/
        }
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
                    Log.i("texte","Email ${emailCliente} já cadastrado, tente outro.")
                    exibirMensagem("Email ${emailCliente} já cadastrado, tente outro.")
                }
            }.addOnFailureListener {
                Log.d("texte", "Error getting documents: ")
            }
    }

    fun confirmaInclusaoCliente() {
        //val cliente = Cliente("1",nomeCliente,emailCliente,incluiTelefone())
        val cliente = Cliente(atualizaIdCliente(),nomeCliente,emailCliente,incluiTelefone())
        if (verificarEmail() && verificarNome()){
            firestore
                .collection( "clientes")
                //.add(atualizaIdCliente())
                .document(atualizaIdCliente())
                .set(cliente)
                .addOnSuccessListener {
                    exibirMensagem("Cliente cadastrado com sucesso!")
                    Log.i("saida","${cliente.nome} ")

                    //startActivity(Intent(applicationContext, LoginActivity::class.java))
                }.addOnFailureListener {
                    exibirMensagem("Erro ao fazer seu cadastro.")
                }
        }

        /*val referencia = firestore.collection("clientes").document()
        referencia.get().addOnCompleteListener { resultado ->
            if (resultado.isSuccessful){
                val valor = resultado.result.id
                val valor1 = resultado.result.data?.get("nome")
                Log.d("texte", "DocumentSnapshot written with ID: ${valor} == $valor1")
            }
        } .addOnFailureListener { e -> Log.i("texte", "Error adding document", e)
        }*/



        /*val referencia = firestore.collection("novos_clientes")
            .add(cliente)
            .addOnSuccessListener { documentReference ->
                Log.d("texte", "DocumentSnapshot written with ID: ${documentReference.id}")
                val valor = documentReference.id
            }
            .addOnFailureListener { e ->
                Log.w("texte", "Error adding document", e)
            }

        val referencia = firestore.collection("novos_clientes").document()
        val saida = referencia.get().addOnSuccessListener { documentSnapshot ->
            Log.i("texte","${documentSnapshot.getDocumentReference("nome")}")
        }
        val outro = referencia.update("nome", "Agepe")
        Log.i("texte","$saida -- $outro")
        //cadastrarIdCliente()

        //nova referwencia
        val novaReferencia = fire
        referencia.get()*/

    }

    private fun atualizaIdCliente(): String {
        val referencia = firestore.collection("clientes").document()

        val saida1 = referencia.id
        val saida = referencia.get().addOnCompleteListener { document ->
            document.result.id
            val olhar = document.result.data?.put("idUser", document.result.id)
            Log.i("saida","Aqui --- ${document.result.id}.")
            Log.i("saida","Alternativa --- ${olhar}.")
            Log.i("saida","Referencia --- ${saida1}.")
        }.addOnFailureListener {
            Log.i("saida","Erro ao ayualizar Id.")
        }
        return saida1.toString()
    }

    private fun cadastrarIdCliente() : String{

        // rascunho
        val referencia = firestore.collection("clientes").document()
        val saida = referencia.get().addOnCompleteListener { document ->
            //document.result.id
            val olhar = document.result.get("nome").toString()
            Log.i("saida","Aqui --- ${document.result.id}.")
            Log.i("saida","Aqui --- ${olhar}.")
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

    fun incluiTelefone() : String {
        if (binding.switchReceberMensagem.isChecked){
            telefoneCliente = binding.editInputTelefoneCliente.text.toString()
            return  telefoneCliente
        }
        return ""
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

    private fun imprimirCliente() {
        firestore.collection("clientes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.i("texte", "${document.id} => ${document.data.get("nome")}")
                }
            }.addOnFailureListener {
                Log.d("texte", "Error getting documents: ")
            }
    }

    /*private fun confirmaInclusaoFuncionario() {
        senhaProvisoria = "12345678"
        if (confirmaEmailFuncionario()) {
            firebaseAuth.cre(
                emailFuncionario, senhaProvisoria
            ).addOnCompleteListener { resultado ->
                if (resultado.isSuccessful) {
                    val idUsuario = resultado.result.user?.uid
                    if (idUsuario != null){
                        val usuario = Usuario(idUsuario, emailFuncionario, senhaProvisoria)
                        salvarFuncionarioFirestores(usuario)
                    }
                    binding.editInputAdministrativaEmail.text = null
                }
                //val idFuncionario = resultado.result.user?.uid
            }.addOnFailureListener { erro ->
                try {
                    throw erro
                } catch (erroUsuarioExistente: FirebaseAuthUserCollisionException) {
                    erroUsuarioExistente.printStackTrace()
                    exibirMensagem("E-mail já percente a outro usuário")
                } catch (erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException) {
                    erroCredenciaisInvalidas.printStackTrace()
                    exibirMensagem("E-mail inválido, digite um outro e-mail")
                }
            }
        }
    }*/


}