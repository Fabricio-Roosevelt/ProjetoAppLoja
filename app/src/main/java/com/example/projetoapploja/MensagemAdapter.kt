package com.example.projetoapploja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MensagemAdapter(
    private val lista: List<Mensagem>
) : Adapter<MensagemAdapter.MensagemViewHolder>() {

    inner class MensagemViewHolder(
        val itemView: View
    ) : ViewHolder(itemView){
        val textNome: TextView = itemView.findViewById(R.id.text_nome)
        val textDescricao: TextView = itemView.findViewById(R.id.text_descricao)
        val textData: TextView = itemView.findViewById(R.id.text_data)

    }

    // Ao criar ViewHolder - cria a visualização
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_lista, parent, false
        )
        return MensagemViewHolder(itemView)
    }

    // ao vincular os dados para a visualização ( view holder)
    override fun onBindViewHolder(mensagemViewHolder: MensagemViewHolder, position: Int) {

        val mensagem = lista[position]
        mensagemViewHolder.textNome.text = mensagem.nome
        mensagemViewHolder.textDescricao.text = mensagem.descricao
        mensagemViewHolder.textData.text = mensagem.data

    }

    // Recupera a quantidade de itens
    override fun getItemCount(): Int {
        return lista.size
    }
}