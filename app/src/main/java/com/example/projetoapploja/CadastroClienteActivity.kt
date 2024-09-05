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
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObjects


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
                Log.i("texte","\"Email ${emailCliente} já cadastrado, tente outro.")
                }
            }.addOnFailureListener {
                Log.d("texte", "Error getting documents: ")
            }
    }

    fun confirmaInclusaoCliente() {
        val cliente = Cliente("1",nomeCliente,emailCliente,incluiTelefone())
        if (verificarEmail() && verificarNome()){
            firestore
                .collection( "clientes")
                .document()
                .set(cliente)
                .addOnSuccessListener {
                    exibirMensagem("Cliente cadastrado com sucesso!")
                    //startActivity(Intent(applicationContext, LoginActivity::class.java))
                }.addOnFailureListener {
                    exibirMensagem("Erro ao fazer seu cadastro.")
                }
        }
        val referencia = firestore.collection("clientes").document()
        val saida = referencia.get().addOnSuccessListener { documentSnapshot ->
            Log.i("texte","${documentSnapshot.getDocumentReference("nome")}")
        }
        Log.i("texte","$saida")
        //cadastrarIdCliente()
    }

    private fun cadastrarIdCliente() {



        /*val novoUser = firestore.collection("clientes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val idUser = document.data.get("userId").toString()
                    val novoId = document.id
                    Log.i("texte", "$idUser -- $novoId")
                }
            }.addOnFailureListener {
                Log.i("texte","Erro ao ayualizar Id.")
            }*/

        //firestore.collection("clientes").document()






        /*val novoId: String
        val novoUser = firestore.collection("clientes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //val idUser = document.data.get("userId").toString()
                    val novoId = document.id
                    Log.i("texte", "$novoId")
                }
                result.query.get()
                Log.i("texte", "Result -> ${result.query}")
            }
            .addOnFailureListener {
                Log.i("texte","Erro ao ayualizar Id.")
            }
        Log.i("texte", "${novoUser}")
       Log.i("texte", "${novoId.toString()} -- $idUser")

        val usuarioId = firestore.collection("clientes").document()
            .update("userId","22")

        val colecao = firestore.collection("clientes").document("")
        colecao
            .update("userId", "541")
            .addOnSuccessListener { Log.i("texte", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.i("texte", "Error updating document", e) }
*/
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