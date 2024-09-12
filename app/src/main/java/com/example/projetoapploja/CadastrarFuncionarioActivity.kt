package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityCadastrarFuncionarioBinding
import com.example.projetoapploja.models.Usuario
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore


class CadastrarFuncionarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarFuncionarioBinding.inflate(layoutInflater)
    }
    var dadosFuncionario = mutableListOf<String>()
    private lateinit var nomeFuncionario: String
    private lateinit var senha: String
    private lateinit var confirmaSenha: String
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
        inicializarToolbar()
    }

    private fun inicializarToolbar() {
        setSupportActionBar(binding.includeCadastrarFuncionario.tbPrincipal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun eventosClique() {
        binding.btnCadastrarFuncionario.setOnClickListener {
            confirmarNome()
            confirmarSenha()
            cadastrarUsuario(nomeFuncionario, senha)
        }
        binding.btnLimpar.setOnClickListener {
            limparCampos()
        }
        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun limparCampos() {
        binding.editTextInputUsuario.text = null
        binding.editTextInputSenha.text = null
        binding.editTextInputConfirmarSenha.text = null
    }

    private fun confirmarNome() : Boolean{
        nomeFuncionario = binding.editTextInputUsuario.text.toString()
        if (nomeFuncionario.isNotEmpty()){
            binding.textInputUsuario.error = null
            return true
        }else{
            binding.textInputUsuario.error = "Nome não pode ser vazio."
            return false
        }
    }

    private fun confirmarSenha() : Boolean {
        senha = binding.editTextInputSenha.text.toString()
        confirmaSenha = binding.editTextInputConfirmarSenha.text.toString()
        if (senha.isNotEmpty()){
            binding.textInputSenha.error = null
            if (senha == confirmaSenha){
                binding.textInputConfirmarSenha.error = null
                return true
            }else{
                binding.textInputConfirmarSenha.error = "As senhas estão diferetes."
                return false
            }
        } else{
            binding.textInputSenha.error = "A senha não pode ser vazia"
            return false
        }
    }

    private fun cadastrarUsuario(nomeFuncionario: String, senha: String) {
        if (confirmarNome() && confirmarSenha()) {
            firebaseAuth.createUserWithEmailAndPassword(
                nomeFuncionario, senha
            ).addOnCompleteListener { resultado ->
                if (resultado.isSuccessful) {
                    val idUsuario = resultado.result.user?.uid
                    if (idUsuario != null){
                        val usuario = Usuario(idUsuario, nomeFuncionario, senha)
                        salvarFuncionarioFirestores(usuario)
                    }
                    limparCampos()
                }
                //val idFuncionario = resultado.result.user?.uid
            }.addOnFailureListener { erro ->
                try {
                    throw erro
                } catch (erroSenhaFraca: FirebaseAuthWeakPasswordException) {
                    erroSenhaFraca.printStackTrace()
                    exibirMensagem("Senha fraca, digite outra com letras, número e caracteres especiais")
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
            .collection("superuser")
            .document(usuario.id)
            .set(usuario)
            .addOnSuccessListener {
                exibirMensagem("Usuario cadastrado com sucesso!")
                startActivity(Intent(applicationContext, LoginActivity::class.java))

            }.addOnFailureListener {
                exibirMensagem("Erro ao fazer seu cadastro.")
            }
    }
}