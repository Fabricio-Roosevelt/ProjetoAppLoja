package com.example.projetoapploja

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetoapploja.databinding.ActivityContatosBinding
import com.example.projetoapploja.databinding.ActivityPrimeiraTelaBinding
import com.example.projetoapploja.fragments.EdicaoItemFragment
import com.example.projetoapploja.fragments.PesquisaFragment

class ContatosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityContatosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}