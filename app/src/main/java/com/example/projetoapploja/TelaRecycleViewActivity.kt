package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TelaRecycleViewActivity : AppCompatActivity() {


    private lateinit var rvLista: RecyclerView
    private lateinit var btnExecutar: Button
    private lateinit var btnEditar: Button
    private lateinit var btnExcluir: Button
    private lateinit var mensagemAdapter: MensagemAdapter

    // aqui vamos fazer com que o app va em busca de atutalizacao na API
    /*override fun onStart() {
        super.onStart()
        atualizar.....
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        setContentView(R.layout.activity_tela_recycle_view)

        // itens para inflar o item de perfil
        val lista = mutableListOf<Mensagem>(
            Mensagem("Fabricio", "Oi", "25/05/2022"),
            Mensagem("José", "Oi tudo bem?", "25/08/2022"),
            //Mensagem("Maria", "Boa tarde", "25/05/2023"),
            //Mensagem("Pedro", "Boa noite", "15/08/2023")
        )

        rvLista = findViewById(R.id.rv_lista)
        btnExecutar = findViewById(R.id.btn_executar)
        btnEditar = findViewById(R.id.btn_editar)
        btnExcluir = findViewById(R.id.btn_excluir)

        // enviados dados dos itens para o recycleview
        mensagemAdapter = MensagemAdapter{ nome ->
            Toast.makeText(this, "Olá $nome", Toast.LENGTH_SHORT).show()

            // enviando dados para outra tela
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nome", nome)

            // abrir outra tela
            startActivity(intent)
        }

        // atualizando lista (NO CASO FINAL USA-SE ONSTART )
        mensagemAdapter.atualizarListaDados(lista)

        // obtendo itens para o recycle view
        rvLista.adapter = mensagemAdapter

        //configurando layout da tela
        rvLista.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // DIVISOR DE ITENS
        rvLista.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )

        // simular atualizacao da lista com o botao EXECUTAR
        btnExecutar.setOnClickListener {
            mensagemAdapter.atualizarListaDados(lista)
        }
        btnEditar.setOnClickListener {
            mensagemAdapter.editarListaDados(lista)
        }
        btnExcluir.setOnClickListener {
            mensagemAdapter.excluirItemListaDados(lista)
        }
    }
}