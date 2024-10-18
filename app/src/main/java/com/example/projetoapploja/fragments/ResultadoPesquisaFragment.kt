package com.example.projetoapploja.fragments

import GENERO
import ID_PRODUTO
import IMAGEM_URL
import MARCA
import MODELO
import PESQUISAR
import PRODUTOS
import TIPO
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoapploja.PesquisaAdapter
import com.example.projetoapploja.R
import com.example.projetoapploja.models.ExibirPesquisa
import com.google.firebase.firestore.FirebaseFirestore



class ResultadoPesquisaFragment : Fragment() {

    private var listaPesquisa = mutableListOf<String>()
    private var listaIdResultado = mutableListOf<String>()
    private var listaProdutos = mutableListOf<ExibirPesquisa>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var pesquisaAdapter: PesquisaAdapter
    private lateinit var idProduto: String
    private lateinit var marcaProduto: String
    private lateinit var tipoProduto: String
    private lateinit var generoProduto: String
    private lateinit var listField: List<String>
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resultado_pesquisa, container, false)

        recyclerView = view.findViewById(R.id.rv_pesquisa_generica)
        recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL, false)
        pesquisaAdapter = PesquisaAdapter(listaProdutos)
        recyclerView.adapter = pesquisaAdapter
        val divisorItens = DividerItemDecoration(recyclerView.context,DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divisorItens)

        return view
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        listaPesquisa.add(args?.getString(PESQUISAR).toString().lowercase())
        pesquisarFirebase(args)
    }

    private fun pesquisarFirebase(args: Bundle?) {
        val nomePesquisa = args?.getString(PESQUISAR).toString().lowercase()
        pesquisarPorMarca(nomePesquisa)
        //pesquisaGenerica(nomePesquisa)
    }

    private fun pesquisaPorModelo(nomePesquisa: String) {
        val referencia = firestore.collection(PRODUTOS)
        val query = referencia.whereGreaterThanOrEqualTo(MODELO,"$nomePesquisa")
            .whereLessThanOrEqualTo(MODELO, nomePesquisa + "\uf8ff")
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get(ID_PRODUTO).toString()
                listaIdResultado.add(idAchado)
                Log.i("saida","ids: $listaIdResultado")
            }
            if (listaIdResultado.isNotEmpty()){
                resultadoFirebase(listaIdResultado)
            }else{
                Log.i("saida","Nenhum item encontrado")
            }
        }
    }

    private fun pesquisarPorTipo(nomePesquisa: String){
        val referencia = firestore.collection(PRODUTOS)
        val query = referencia.whereGreaterThanOrEqualTo(TIPO,"$nomePesquisa")
            .whereLessThanOrEqualTo(TIPO, nomePesquisa + "\uf8ff")
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get(ID_PRODUTO).toString()
                listaIdResultado.add(idAchado)
                Log.i("saida","ids: $listaIdResultado")
            }
            if (listaIdResultado.isNotEmpty()){
                resultadoFirebase(listaIdResultado)
            }else{
                pesquisarPorGenero(nomePesquisa)
            }
        }
    }

    private fun pesquisarPorGenero(nomePesquisa: String) {
        val referencia = firestore.collection(PRODUTOS)
        val query = referencia.whereGreaterThanOrEqualTo(GENERO,"$nomePesquisa")
            .whereLessThanOrEqualTo(GENERO, nomePesquisa + "\uf8ff")
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get(ID_PRODUTO).toString()
                listaIdResultado.add(idAchado)
            }
            if (listaIdResultado.isNotEmpty()){
                resultadoFirebase(listaIdResultado)
            }else{
                pesquisaPorModelo(nomePesquisa)
            }
        }
    }

    private fun pesquisarPorMarca(nomePesquisa: String) {
        val referencia = firestore.collection(PRODUTOS)
        val query = referencia.whereGreaterThanOrEqualTo(MARCA,nomePesquisa)
            .whereLessThanOrEqualTo(MARCA, nomePesquisa + "\uf8ff")
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get(ID_PRODUTO).toString()
                listaIdResultado.add(idAchado)
            }
            if (listaIdResultado.isNotEmpty()){
                resultadoFirebase(listaIdResultado)
            }else{
                pesquisarPorTipo(nomePesquisa)
            }
        }
    }

    fun resultadoFirebase(listaIdResultado: MutableList<String>) {
        val referencia = firestore.collection(PRODUTOS)
        for (item in listaIdResultado){
            referencia.document(item)
                .get()
                .addOnSuccessListener { document ->
                    idProduto = document.data?.get(ID_PRODUTO).toString()
                    marcaProduto = document.data?.get(MARCA).toString()
                    tipoProduto = document.data?.get(TIPO).toString()
                    generoProduto = document.data?.get(GENERO).toString()
                    val uriList = document.data?.get(IMAGEM_URL) as MutableList<String>
                    val imagem = uriList[0]
                    val produto = ExibirPesquisa(idProduto,marcaProduto,tipoProduto,generoProduto,imagem)
                    listaProdutos.add(produto)
                    recyclerView.adapter = PesquisaAdapter(listaProdutos)
                }.addOnFailureListener { e ->
                    Log.i("saida","Erro: $e")
                }
        }
    }
}