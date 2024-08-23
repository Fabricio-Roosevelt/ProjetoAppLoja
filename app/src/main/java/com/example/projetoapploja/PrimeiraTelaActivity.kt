package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.projetoapploja.databinding.ActivityPrimeiraTelaBinding
import com.example.projetoapploja.fragments.AdicaoItemFragment
import com.example.projetoapploja.fragments.PesquisaFragment


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
        binding.btnNovidades.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        binding.btnPesquisar.setOnClickListener {
            startActivity(Intent(this, PesquisaActivity::class.java))

        }
        binding.btnContatos.setOnClickListener {

        }
        binding.btnCadastro.setOnClickListener {

        }
        binding.btnAdministrador.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}