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

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarEventosClique()

        binding.btnEntrar.setOnClickListener {
            //startActivity(Intent(this, MainActivity::class.java))
            startActivity(Intent(this, TesteActivity::class.java))
        }

    }

    private fun inicializarEventosClique() {
        binding.textCadastro.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
    }
}