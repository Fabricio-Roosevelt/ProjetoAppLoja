package com.example.projetoapploja.fragments

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
        listaPesquisa.add(args?.getString("pesquisar").toString())
        pesquisarFirebase(args)
    }

    private fun pesquisarFirebase(args: Bundle?) {
        val nomePesquisa = args?.getString("pesquisar")
        pesquisarPorMarca(nomePesquisa)

    }

    private fun pesquisarPorTipo(nomePesquisa: String?){
        val referencia = firestore.collection("produtos")
        Log.i("saida","no tipo: $nomePesquisa")
        val query = referencia.whereEqualTo("tipo", nomePesquisa)
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get("idProduto").toString()
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

    private fun pesquisarPorGenero(nomePesquisa: String?) {
        val referencia = firestore.collection("produtos")
        Log.i("saida","no tipo: $nomePesquisa")
        val query = referencia.whereEqualTo("genero", nomePesquisa)
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get("idProduto").toString()
                listaIdResultado.add(idAchado)
            }
            if (listaIdResultado.isNotEmpty()){
                resultadoFirebase(listaIdResultado)
            }else{
                Log.i("saida","Nenhum item encontrado")
            }
        }
    }

    private fun pesquisarPorMarca(nomePesquisa: String?) {
        val referencia = firestore.collection("produtos")
        val query = referencia.whereEqualTo("marca", nomePesquisa)
        query.addSnapshotListener { querySnapshop, erro ->
            val documentos = querySnapshop?.documents
            documentos?.forEach { documentSnapshot ->
                val idAchado = documentSnapshot.get("idProduto").toString()
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
        val referencia = firestore.collection("produtos")
        for (item in listaIdResultado){
            referencia.document(item)
                .get()
                .addOnSuccessListener { document ->
                    idProduto = document.data?.get("idProduto").toString()
                    marcaProduto = document.data?.get("marca").toString()
                    tipoProduto = document.data?.get("tipo").toString()
                    generoProduto = document.data?.get("genero").toString()
                    val uriList = document.data?.get("imagemUrl") as MutableList<String>
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