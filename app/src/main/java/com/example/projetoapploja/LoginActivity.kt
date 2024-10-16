package com.example.projetoapploja

import EMAIL_NAO_CADATRADO
import EMAIL_SENHA_INVALIDO
import ERRO_SENHA_VAZIA
import USUARIO_LOGADO_SUCESSO
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityLoginBinding
import com.example.projetoapploja.utils.exibirMensagem
import com.example.projetoapploja.utils.validarSuperUsuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

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
            startActivity(Intent(this, CadastrarFuncionarioActivity::class.java))
        }
        binding.textCadastrarCliente.setOnClickListener {
            startActivity(Intent(this, CadastroClienteActivity::class.java))

        }
        binding.btnEntrar.setOnClickListener {
            startActivity(Intent(this, TelaAdministrativaActivity::class.java))
            // desmarcar apos a conclusao de usuarios e senhas
            /*if (verificarCampos()) {
                logarUsuario()
            }*/
        }
    }

    private fun logarUsuario() {
        firebaseAuth.signInWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener {
            exibirMensagem(USUARIO_LOGADO_SUCESSO)
            if (validarSuperUsuario(email,senha)) {
                startActivity(Intent(this, TelaAdministrativaActivity::class.java))
            }else if (email=="jose@gmail.com" && senha=="12345678"){
                startActivity(Intent(this, TelaAdministrativaActivity::class.java))
            }
            else{
                startActivity(Intent(this, PesquisaActivity::class.java))
            }
        }.addOnFailureListener { erro ->
            try {
                throw erro
            } catch (erroUsuarioInvalido: FirebaseAuthInvalidUserException) {
                erroUsuarioInvalido.printStackTrace()
                exibirMensagem(EMAIL_NAO_CADATRADO)
            } catch (erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException) {
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem(EMAIL_SENHA_INVALIDO)
            }
        }
    }

    private fun verificarCampos() : Boolean {
        email = binding.editInputLayoutEmail.text.toString()
        senha = binding.editInputLoginSenha.text.toString()

        if (email.isNotEmpty()){
            binding.textInputEmailCliente.error = null
            if (senha.isNotEmpty()){
                binding.textInputLoginSenha.error = null
                return true
            }else{
                binding.textInputLoginSenha.error = ERRO_SENHA_VAZIA
                return false
            }
        }else{
            binding.textInputEmailCliente.error = "Preencha o email"
            return false
        }
    }
}