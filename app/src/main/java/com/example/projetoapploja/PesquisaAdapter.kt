package com.example.projetoapploja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetoapploja.models.ExibirPesquisa

class PesquisaAdapter(
    private val listaResultados: MutableList<ExibirPesquisa>
) : RecyclerView.Adapter<PesquisaAdapter.PesquisaViewHolder>(){

    inner class PesquisaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imagemView: ImageView = itemView.findViewById(R.id.image_perfil)
        val marcaView: TextView = itemView.findViewById(R.id.text_marca)
        val tipoView: TextView = itemView.findViewById(R.id.text_tipo)
        val generoView: TextView = itemView.findViewById(R.id.text_genero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesquisaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista, parent, false)
        return PesquisaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesquisaViewHolder, position: Int) {
        val produto = listaResultados[position]

        holder.marcaView.text = produto.marca
        holder.tipoView.text = produto.tipo
        holder.generoView.text = produto.genero

        if (produto.imagemUrl.isNotEmpty()){
            Glide.with(holder.imagemView.context)
                .load(produto.imagemUrl)
                .override(80,80)
                .into(holder.imagemView)
        }else{
            holder.imagemView.setImageResource(R.drawable.ic_oculos)
        }
    }

    override fun getItemCount(): Int {
        return listaResultados.size
    }
}