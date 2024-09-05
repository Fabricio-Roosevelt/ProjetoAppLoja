package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityTelaAdministrativaBinding
import com.example.projetoapploja.models.Usuario
import com.example.projetoapploja.utils.exibirMensagem
import com.example.projetoapploja.utils.validarSuperUsuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore


class TelaAdministrativaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaAdministrativaBinding.inflate(layoutInflater)
    }

    private lateinit var emailFuncionario: String
    private lateinit var senhaProvisoria: String

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        eventosClique()

    }

    private fun eventosClique() {
        binding.btnAdministrativoCadastrarProduto.setOnClickListener {
            startActivity(Intent(this, CadastrarProdutoActivity::class.java))
        }
        binding.btnAdministrativoCadastrarFuncionario.setOnClickListener {
            cadastroVisibilidade()
        }
        binding.btnAdministrativoRemoverFuncionario.setOnClickListener {
            cadastroVisibilidade()
        }
        binding.btnAdministrativaCancelar.setOnClickListener {
            binding.editInputAdministrativaEmail.text = null
        }
        binding.btnAdministrativaConfirmar.setOnClickListener {
            confirmaEmailFuncionario()
            confirmaInclusaoFuncionario()
        }
        binding.btnAdministrativoAdicionarCliente.setOnClickListener {
            startActivity(Intent(this, CadastroClienteActivity::class.java))
        }
    }

    private fun cadastroVisibilidade() {
        binding.rgAdministrativoFuncao.visibility =
            if (binding.rgAdministrativoFuncao.visibility == View.VISIBLE){
                View.INVISIBLE
            }else View.VISIBLE
        binding.textInputAdministrativaEmail.visibility =
            if (binding.textInputAdministrativaEmail.visibility == View.VISIBLE){
                View.INVISIBLE
            }else View.VISIBLE
        binding.btnAdministrativaCancelar.visibility =
            if (binding.btnAdministrativaCancelar.visibility == View.VISIBLE){
                View.INVISIBLE
            }else View.VISIBLE
        binding.btnAdministrativaConfirmar.visibility =
            if (binding.btnAdministrativaConfirmar.visibility == View.VISIBLE){
                View.INVISIBLE
            }else View.VISIBLE
    }

    private fun confirmaEmailFuncionario() : Boolean {
        emailFuncionario = binding.editInputAdministrativaEmail.text.toString()
        if (emailFuncionario.isNotEmpty()){
            binding.textInputAdministrativaEmail.error = null
            return true
        }else{
            binding.textInputAdministrativaEmail.error = "Nome não pode ser vazio."
            return false
        }
    }

    private fun confirmaInclusaoFuncionario() {
        senhaProvisoria = "12345678"
        if (confirmaEmailFuncionario()) {
            firebaseAuth.createUserWithEmailAndPassword(
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
    }

    private fun salvarFuncionarioFirestores(usuario: Usuario) {
        firestore
            .collection( if (binding.rbAdministrador.isChecked){
                "administradores"
            }else{
                "funcionarios"
            }
            )
            .document(usuario.id)
            .set(usuario)
            .addOnSuccessListener {
                exibirMensagem("Usuario cadastrado com sucesso!")
                //startActivity(Intent(applicationContext, LoginActivity::class.java))

            }.addOnFailureListener {
                exibirMensagem("Erro ao fazer seu cadastro.")
            }
    }

}