package com.example.projetoapploja

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoapploja.databinding.ActivityTelaRecycleViewBinding

class TelaRecycleViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaRecycleViewBinding.inflate(layoutInflater)
    }
    private lateinit var rvList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lista = listOf(
            Mensagem("Fabricio", "Oi", "25/05/2022"),
            Mensagem("José", "Oi tudo bem?", "25/08/2022"),
            Mensagem("Maria", "Boa tarde", "25/05/2023"),
            Mensagem("Pedro", "Boa noite", "15/08/2023")
        )

        rvList = binding.rvLista
        rvList.adapter = MensagemAdapter(lista)
        rvList.layoutManager = LinearLayoutManager(this)
    }
}