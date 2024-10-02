package com.example.projetoapploja

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RecycleViewImagens(
    private val clique: Int   // dados para enviar no clique
) : RecyclerView.Adapter<RecycleViewImagens.MensagemViewHolder>() {


    private var listaImagens = mutableListOf<Image>()


    inner class MensagemViewHolder(
        val itemView: View
    ) : ViewHolder(itemView){
        val imagePerfil: ImageView = itemView.findViewById(R.id.imageCardProduto)
        fun bind(){
            imagePerfil.setOnClickListener {
                Log.i("saida","RecylceView deu certo")
            }
        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecycleViewImagens.MensagemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_card_view, parent, false
        )
        return MensagemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        val mensagem = listaImagens[position]
        holder.bind()
    }


    override fun getItemCount(): Int {
        return listaImagens.size
    }

}