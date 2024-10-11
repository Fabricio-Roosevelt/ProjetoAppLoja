package com.example.projetoapploja

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImagemAdapter (
    private val imagemUris: List<Uri>
): RecyclerView.Adapter<ImagemAdapter.ImagemViewHolder>(){
    inner class ImagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imagemView: ImageView = itemView.findViewById(R.id.imageCardProduto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_view, parent, false)
        return ImagemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagemViewHolder, position: Int) {
        val imagemUri = imagemUris[position]
        Glide.with(holder.imagemView.context)
            .load(imagemUri)
            .into(holder.imagemView)
    }

    override fun getItemCount(): Int {
        return imagemUris.size
    }

}

