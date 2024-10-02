package com.example.projetoapploja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView


class ImagemAdapter(
    private val minhasImagens: ArrayList<Imagens>
) : RecyclerView.Adapter<ImagemAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = minhasImagens[position]
        holder.titleImage.setImageResource(0)
    }

    override fun getItemCount(): Int {
        return minhasImagens.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleImage: ShapeableImageView = itemView.findViewById(R.id.text_nome_teste)
    }

}