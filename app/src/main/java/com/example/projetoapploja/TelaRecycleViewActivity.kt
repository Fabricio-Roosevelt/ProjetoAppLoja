package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoapploja.databinding.ActivityTelaRecycleViewBinding

class TelaRecycleViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaRecycleViewBinding
    private lateinit var mensagemAdapter: MensagemAdapter

    // aqui vamos fazer com que o app va em busca de atutalizacao na API
    /*override fun onStart() {
        super.onStart()
        atualizar.....
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaRecycleViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // itens para inflar o item de perfil
        val lista = mutableListOf<Mensagem>(
            Mensagem("Fabricio", "Oi", "25/05/2022"),
            Mensagem("José", "Oi tudo bem?", "25/08/2022"),
            //Mensagem("Maria", "Boa tarde", "25/05/2023"),
            //Mensagem("Pedro", "Boa noite", "15/08/2023")
        )

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
        binding.rvLista.adapter = mensagemAdapter

        //configurando layout da tela
        binding.rvLista.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        // DIVISOR DE ITENS
        binding.rvLista.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )

        // simular atualizacao da lista com o botao EXECUTAR
        with(binding) {
            btnExecutar.setOnClickListener {
                mensagemAdapter.atualizarListaDados(lista)
            }
            btnEditar.setOnClickListener {
                mensagemAdapter.editarListaDados(lista)
            }
            btnExcluir.setOnClickListener { view ->
                mensagemAdapter.excluirItemListaDados(lista)
                caixaDiaologoAlerta()

            }
            rvLista.setOnClickListener {
                startActivity(
                    intent
                )
            }
        }
    }

    // caixa de dialogo para confirmar exclusao de item
    private fun caixaDiaologoAlerta() {

        AlertDialog.Builder(this)
            .setTitle("Confirmar exclusão do item!")
            .setMessage("Tem certeza disso?")
            .setNegativeButton("cancelar"){dialog, posicao ->

                Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show()
                //dialog.cancel()
            }
            .setPositiveButton("Excluir"){dialog, posicao ->
                Toast.makeText(this, "Item excluido", Toast.LENGTH_SHORT).show()
                //dialog.cancel()
            }
            .setCancelable(false)
            .create()
            .show()
    }
}