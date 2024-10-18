package com.example.projetoapploja

import ERRO_UP_LOAD_IMAGENS_FIREBASE
import ID_PASTA_FOTOS
import IMAGEM_URL
import INSERIR_FOTOS_PRODUTO
import MARCA
import MODELO
import NOVIDADE
import PRODUTOS
import PRODUTO_ERRO_CADASTRO
import SEXO
import TIPO
import UP_LOAD_IMAGENS_FIREBASE
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.projetoapploja.databinding.ActivityCadastrarProdutoBinding
import com.example.projetoapploja.fragments.AdicionarFotosFragment
import com.example.projetoapploja.fragments.CadastrarProdutoTela1Fragment
import com.example.projetoapploja.models.Produto
import com.example.projetoapploja.utils.exibirMensagem
import com.google.firebase.firestore.FirebaseFirestore


class CadastrarProdutoActivity : AppCompatActivity(), ProdutosNovosInsterface, AdicionarFotosFragment.OnDataPass {

    private val binding by lazy {
        ActivityCadastrarProdutoBinding.inflate(layoutInflater)
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private var listaImagens: MutableList<String> = mutableListOf()
    private lateinit var pastaProdutos: String

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
    // Lidando com o clique no botão de voltar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Volta ao fragmento anterior
        return true
    }

    override fun transferirDadosNovoProduto(mensagem: MutableMap<String, String>) {
        val referencia = firestore.collection(PRODUTOS).document()
        val idProduto = referencia.id
        val marcaProduto = "${mensagem.get(MARCA)}".lowercase()
        val tipoProdtuo = "${mensagem.get(TIPO)}".lowercase()
        val generoProduto = "${mensagem.get(SEXO)}".lowercase()
        val novidadeProduto = "${mensagem.get(NOVIDADE)}".lowercase()
        val modeloProduto = "${mensagem.get(MODELO)}".lowercase()

        val produto = Produto(
            idProduto,
            marcaProduto,
            tipoProdtuo,
            generoProduto,
            novidadeProduto,
            modeloProduto,
            mutableListOf()
        )
        cadastrarProduto(produto)
    }

    // recebendo dados da galeria de fotos
    override fun onDataPass(data: MutableList<String>) {
        listaImagens = data
        processarImagens()
    }

    // copiando imagens recebidas para a uma lista que será adicionada ao firebase
    private fun processarImagens() : MutableList<String> {
        if (listaImagens.isNotEmpty()){
            atualizarImagens(listaImagens)
            return listaImagens
        }
        return mutableListOf()
    }

    // atualiza array de imagens na tabela do firebase
    private fun atualizarImagens(listaImagens: MutableList<String>) {
        val listaAtualizada = listaImagens
        val docReferencia = firestore.collection(PRODUTOS).document(pastaProdutos)
        docReferencia.update(IMAGEM_URL, listaAtualizada)
            .addOnSuccessListener {
                exibirMensagem(UP_LOAD_IMAGENS_FIREBASE)
                listaImagens.clear()
                atualizarPastasDocumentos()
            }.addOnFailureListener {
                exibirMensagem(ERRO_UP_LOAD_IMAGENS_FIREBASE)
            }
    }

    // remover quaisquer cadastro adicionados sem imagens
    private fun atualizarPastasDocumentos(){
        val colecaoReferencia = firestore.collection(PRODUTOS)
        colecaoReferencia.get()
            .addOnSuccessListener { resultado ->
                if(!resultado.isEmpty){
                    for (documento in resultado){
                        val imagensProdutos = documento.data.getValue(IMAGEM_URL) as? ArrayList<String>
                        if (imagensProdutos != null){
                            if (imagensProdutos.isEmpty()){
                                colecaoReferencia.document(documento.id)
                                    .delete()
                                    .addOnSuccessListener {
                                        Log.i("saida", "Documento vazio removido com sucesso.")
                                    }.addOnFailureListener { e ->
                                        Log.i("saida", "Erro ao apagar: ${e.message}")
                                    }
                            }
                        }
                    }
                }else{
                    Log.i("saida","Nenhum documento encontrado na coleção.")
                }
            }.addOnFailureListener { e ->
                Log.i("saida","Erro ao listar documento: ${e.message}")
            }
    }

    // cadastra os produtos no base de dados - firebase
    private fun cadastrarProduto(produto: Produto){
        if (listaImagens.isNotEmpty()) {
            produto.imagemUrl = listaImagens
        }
        //Log.i("saida","$produto")
        firestore
            .collection( PRODUTOS)
            .document(produto.idProduto)
            .set(produto)
            .addOnSuccessListener {
                exibirMensagem(INSERIR_FOTOS_PRODUTO)
                val adicionarFotosFragment = AdicionarFotosFragment()
                val bundle = bundleOf(
                    ID_PASTA_FOTOS to produto.idProduto
                )
                adicionarFotosFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_cadastro, adicionarFotosFragment)
                    .addToBackStack(null)
                    .commit()
            }.addOnFailureListener {
                exibirMensagem(PRODUTO_ERRO_CADASTRO)
            }
        pastaProdutos = produto.idProduto
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