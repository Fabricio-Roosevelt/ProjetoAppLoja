package com.example.projetoapploja

import CLIENTES
import CLIENTE_CADASTRO_SUCESSO
import EMAIL
import ERRO_EMAIL_VAZIO
import ERRO_NOME_VAZIO
import ERRO_TELEFONE_INVALIDO
import ERRO_TELEFONE_VAZIO
import USUARIO_ERRO_CADASTRO
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityCadastroClienteBinding
import com.example.projetoapploja.models.Cliente
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Matcher
import java.util.regex.Pattern


class CadastroClienteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroClienteBinding.inflate(layoutInflater)
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private lateinit var nomeCliente: String
    private lateinit var emailCliente: String
    private lateinit var telefoneCliente: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        switchNotificacoes()
        inicializarToolbar()
        eventosClique()
    }

    private fun eventosClique() {
        binding.btnCadastrarCliente.setOnClickListener {
            nomeCliente = binding.editTextNomeCliente.text.toString()
            emailCliente = binding.editTextEmailCliente.text.toString()
            verificarNome()
            verificarEmail()
            incluirWhatsApp()
            listaQuerReceberNovidades()
            verificarDuplicidadeEmail()
        }
        binding.includeCadastroCliente.btnEditarCliente.setOnClickListener {
            startActivity(Intent(this, EditarCadastroClienteActivity::class.java))
        }
    }

    private fun switchNotificacoes(){
        binding.switchReceberMensagem.setOnClickListener {
            binding.textNotificacaoMensagem.visibility =
                if (binding.switchReceberMensagem.isChecked) View.VISIBLE
                else View.INVISIBLE
            binding.textInputTelefoneCliente.visibility =
                if (binding.switchReceberMensagem.isChecked) View.VISIBLE
                else View.INVISIBLE
        }
    }

    private fun incluirWhatsApp(): Boolean{
        if (binding.switchReceberMensagem.isChecked){
            return true
        }else{
            return false
        }
    }

    private fun confirmarInclusaoTelefone() : Boolean{
        if (incluirWhatsApp()) {
            try {
                if (verificarCampoTelefone()) {
                    if (formatarTelefone().isNotEmpty()) {
                        confirmarInclusao()
                        return true
                    }
                }
            } catch (e: Exception) {
                Log.i("saida", ERRO_TELEFONE_INVALIDO)
            }
        }
        return false
    }

    private fun confirmarInclusao(){
        val telefone = formatarTelefone()
        if (!incluirWhatsApp()){
            confirmaInclusaoCliente()
        } else if (incluirWhatsApp() && telefone.isNotEmpty()){
            confirmaInclusaoCliente()
        }else {
            Log.d("saida", ERRO_TELEFONE_VAZIO)
        }
    }

    fun verificarCampoTelefone() : Boolean{
        val numeroTelefone = binding.editInputTelefoneCliente.text.toString()
        if (numeroTelefone.isNotEmpty()){
            binding.textInputTelefoneCliente.error = null
            return true
        }else{
            binding.textInputTelefoneCliente.error = ERRO_TELEFONE_VAZIO
            return false
        }
    }

    fun formatarTelefone() : String{
        telefoneCliente = binding.editInputTelefoneCliente.text.toString()
        if (incluirWhatsApp() && verificarCampoTelefone()) {
            try {
                val numeroTefefoneFormatado = PhoneNumberUtils.formatNumberToE164(telefoneCliente, "BR")
                return numeroTefefoneFormatado
            } catch (e: Exception) {
                exibirMensagem(ERRO_TELEFONE_INVALIDO)
            }
        }
        return ""
    }

    private fun verificarNome() : Boolean{
        nomeCliente = binding.editTextNomeCliente.text.toString()
        if (nomeCliente.isNotEmpty()){
            binding.textInputNomeCliente.error = null
            return true
        }else{
            binding.textInputNomeCliente.error = ERRO_NOME_VAZIO
            return false
        }
    }

    private fun verificarEmail() : Boolean{
        emailCliente = binding.editTextEmailCliente.text.toString()
        if (emailCliente.isNotEmpty()){
            binding.editTextEmailCliente.error = null
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
            binding.editTextEmailCliente.error = ERRO_EMAIL_VAZIO
            return false
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
        firestore.collection(CLIENTES)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val emailComparacao = document.data.get(EMAIL).toString()
                    if (emailComparacao == emailCliente) {
                        emailsIguais += 1
                    }
                }
                if (emailsIguais == 0){
                    Log.i("saida","OK, o email: $emailCliente pode ser adicionado.")
                    if (incluirWhatsApp()){
                        confirmarInclusaoTelefone()
                    }else{
                        confirmaInclusaoCliente()
                    }
                }else{
                    Log.i("saida","Email ${emailCliente} já cadastrado, tente outro.")
                    exibirMensagem("Email ${emailCliente} já cadastrado, tente outro.")
                }
            }.addOnFailureListener {
                Log.d("saida", "Error getting documents: ")
            }
    }

    private fun confirmaInclusaoCliente() {
        val referencia = firestore.collection(CLIENTES).document()
        val idUsuario = referencia.id
        val recerNovidadeEmail = listaQuerReceberNovidades()
        val reberWhatsApp = incluirWhatsApp()
        val numeroTelefoneFormatado = formatarTelefone()

        val cliente = Cliente(
            idUsuario,
            nomeCliente,
            emailCliente,
            numeroTelefoneFormatado,
            recerNovidadeEmail,
            reberWhatsApp
        )
        if (verificarEmail() && verificarNome()){
            firestore
                .collection(CLIENTES)
                .document(idUsuario)
                .set(cliente)
                .addOnSuccessListener {
                    exibirMensagem(CLIENTE_CADASTRO_SUCESSO)
                    limparCampos()
                    //startActivity(Intent(applicationContext, LoginActivity::class.java))
                }.addOnFailureListener {
                    exibirMensagem(USUARIO_ERRO_CADASTRO)
                }
        } else{
            exibirMensagem("Email e/ou formato invalido. " +
                    "\nFavor inserir um email válido." +
                    "\nEx.: maria@email.com")
        }
    }

    private fun limparCampos() {
        binding.switchReceberEmail.isChecked = false
        binding.switchReceberMensagem.isChecked = false
        binding.textNotificacaoMensagem.visibility = View.INVISIBLE
        binding.editInputTelefoneCliente.text = null
        binding.textInputTelefoneCliente.visibility = View.INVISIBLE
    }

    private fun inicializarToolbar() {
        setSupportActionBar(binding.includeCadastroCliente.tbAlternativa)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}