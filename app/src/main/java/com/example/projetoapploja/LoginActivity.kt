package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetoapploja.databinding.ActivityLoginBinding
import com.example.projetoapploja.databinding.ActivityMainBinding
import com.example.projetoapploja.fragments.AdicaoItemFragment
import com.example.projetoapploja.fragments.EdicaoItemFragment
import com.example.projetoapploja.fragments.Tela1CadastroFragment
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var email: String
    private lateinit var senha: String
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarEventosClique()

    }

    private fun inicializarEventosClique() {
        binding.textCadastrarFuncionario.setOnClickListener {
            startActivity(Intent(this, TelaAdministrativaActivity::class.java))
        }
        binding.textCadastrarCliente.setOnClickListener {
            startActivity(Intent(this, TelaAdministrativaActivity::class.java))
        }
        binding.btnEntrar.setOnClickListener {
            if (verificarCampos()) {
                logarUsuario()
            }
        }
    }

    private fun logarUsuario() {
        firebaseAuth.signInWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener {
            exibirMensagem("Logado com sucesso!")
            startActivity(Intent(this, CadastrarProdutoActivity::class.java))
        }.addOnFailureListener { erro ->
            try {
                throw erro
            } catch (erroUsuarioInvalido: FirebaseAuthInvalidUserException) {
                erroUsuarioInvalido.printStackTrace()
                exibirMensagem("Email não cadastrado.")
            } catch (erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException) {
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("E-mail ou senha inválidos.")
            }
        }
    }

    private fun verificarCampos() : Boolean {
        email = binding.editInputLayoutEmail.text.toString()
        senha = binding.editInputLoginSenha.text.toString()

        if (email.isNotEmpty()){
            binding.textInputLoginEmail.error = null
            if (senha.isNotEmpty()){
                binding.textInputLoginSenha.error = null
                return true
            }else{
                binding.textInputLoginSenha.error = "Senha não pode ser vazia."
                return false
            }
        }else{
            binding.textInputLoginEmail.error = "Preencha o email"
            return false
        }

    }
}