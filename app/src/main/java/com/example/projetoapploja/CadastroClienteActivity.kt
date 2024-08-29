package com.example.projetoapploja

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.projetoapploja.databinding.ActivityCadastroClienteBinding


class CadastroClienteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroClienteBinding.inflate(layoutInflater)
    }
    var dadosCliente = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        switchNotificacoes()

        binding.btnCadastrarCliente.setOnClickListener {
            val nomeCliente = binding.editTextNomeCliente.text.toString()
            val emailCliente = binding.editTextEmailCliente.text.toString()
            val telefoneCliente = binding.editTextInputTelefone.text.toString()

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
            dadosCliente.clear()
        }

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