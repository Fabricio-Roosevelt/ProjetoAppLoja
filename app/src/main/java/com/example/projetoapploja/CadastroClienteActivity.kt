package com.example.projetoapploja

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
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
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
                Log.i("saida", "Erro ao adicionar numero.")
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
            Log.d("saida", "Não pode confirmar inclusão, não pode ser vazio.")
        }
    }

    fun verificarCampoTelefone() : Boolean{
        val numeroTelefone = binding.editInputTelefoneCliente.text.toString()
        if (numeroTelefone.isNotEmpty()){
            binding.textInputTelefoneCliente.error = null
            return true
        }else{
            binding.textInputTelefoneCliente.error = "Telefone não pode ser VAZIO."
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
                exibirMensagem("Numero invalido. \nDigite com o DDD (Ex: 84) e sem espaço entre os números.")
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
            binding.textInputNomeCliente.error = "Nome não pode ser vazio."
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
            binding.editTextEmailCliente.error = "Email não pode ser vazio."
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
        val referencia = firestore.collection("clientes").document()
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
                .collection( "clientes")
                .document(idUsuario)
                .set(cliente)
                .addOnSuccessListener {
                    exibirMensagem("Cliente cadastrado com sucesso!")
                    limparCampos()
                    //startActivity(Intent(applicationContext, LoginActivity::class.java))
                }.addOnFailureListener {
                    exibirMensagem("Erro ao fazer seu cadastro.")
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