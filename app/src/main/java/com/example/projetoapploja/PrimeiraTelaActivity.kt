package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityPrimeiraTelaBinding


class PrimeiraTelaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPrimeiraTelaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        eventosClique()

    }

    private fun eventosClique() {
        binding.textAdmin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnPesquisaDetalhada.setOnClickListener {
            startActivity(Intent(this, PesquisaActivity::class.java))
        }
        binding.btnPesquisaGenerica.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.textContatos.setOnClickListener {
            startActivity(Intent(this, ContatosActivity::class.java))
        }
        binding.btnCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroClienteActivity::class.java))
        }
    }
}