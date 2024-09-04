package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityTelaAdministrativaBinding


class TelaAdministrativaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaAdministrativaBinding.inflate(layoutInflater)
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
            startActivity(Intent(this, CadastrarFuncionarioActivity::class.java))
        }
        binding.btnAdministrativoAdicionarCliente.setOnClickListener {
            startActivity(Intent(this, CadastroClienteActivity::class.java))
        }
    }
}