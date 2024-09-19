package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projetoapploja.databinding.ActivityCadastrarProdutoBinding
import com.example.projetoapploja.fragments.AdicaoItemFragment
import com.example.projetoapploja.fragments.CadastrarProdutoTela1Fragment
import com.example.projetoapploja.fragments.CadastroProdutoTela2Fragment
import com.example.projetoapploja.fragments.EdicaoItemFragment
import com.example.projetoapploja.models.Cliente
import com.example.projetoapploja.models.Produto
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore


class CadastrarProdutoActivity : AppCompatActivity(), ProdutosNovosInsterface {

    private val binding by lazy {
        ActivityCadastrarProdutoBinding.inflate(layoutInflater)
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //supportActionBar?.hide()   // retirar actionbar da tela
        inicializarToolbar()
        iniciarFragments()
    }

    private fun iniciarFragments() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_cadastro, CadastrarProdutoTela1Fragment())
            .commit()
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        binding.includeToolbar.tbPrincipal.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeToolbar.tbPrincipal.overflowIcon.apply {
            getColor(R.color.white)
        }
        supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun transferirDadosNovoProduto(mensagem: MutableMap<String, String>) {
        val referencia = firestore.collection("produtos").document()
        val idProduto = referencia.id
        val marcaProduto = "${mensagem.get("marca")}"
        val tipoProdtuo = "${mensagem.get("tipo")}"
        val generoProduto = "${mensagem.get("sexo")}"
        val novidadeProduto = "${mensagem.get("novidade")}"
        val modeloProduto = "${mensagem.get("modelo")}"

        val produto = Produto(
            idProduto,
            marcaProduto,
            tipoProdtuo,
            generoProduto,
            novidadeProduto,
            modeloProduto,
        )
        firestore
            .collection( "produtos")
            .document(idProduto)
            .set(produto)
            .addOnSuccessListener {
                exibirMensagem("Produto cadastrado com sucesso!")
                startActivity(Intent(applicationContext, CadastrarProdutoActivity::class.java))
            }.addOnFailureListener {
                exibirMensagem("Erro ao fazer seu cadastro.")
            }
    }

    /*
   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_alternativo, menu)
       supportActionBar?.apply {
           title = "Cadastre um produto"
           setDisplayHomeAsUpEnabled(true)
       }
       binding.includeToolbar.tbPrincipal.setOnMenuItemClickListener { menuItem ->
           when (menuItem.itemId){
               R.id.itemPesquisar -> {
                   startActivity(Intent(this, PesquisaActivity::class.java))
                   return@setOnMenuItemClickListener true
               }
               R.id.itemEditar -> {
                   Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
                   return@setOnMenuItemClickListener true
               }
               R.id.itemSair -> {
                   startActivity(Intent(this, PrimeiraTelaActivity::class.java))
                   return@setOnMenuItemClickListener true
               }else -> {
                   return@setOnMenuItemClickListener true
               }
           }
       }
       return true
   }
   */
}