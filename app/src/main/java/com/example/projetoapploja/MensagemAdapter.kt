package com.example.projetoapploja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar

class MensagemAdapter(
    private val clique: (String) -> Unit   // dados para enviar no clique
) : Adapter<MensagemAdapter.MensagemViewHolder>() {

    // atauliza a lista de nomes e mensagens
    private var listaMensagens = mutableListOf<Mensagem>()

    // atualizando a lista
    fun atualizarListaDados(lista: MutableList<Mensagem>){
        listaMensagens.add(
            Mensagem("Novo ITEM", "Deu certo", "01/02/2020")
        )
        //Toast.makeText(this, "ITEM COM SUCESSO", Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, "Item adicionado com sucesso.", Toast.LENGTH_SHORT).show()
        listaMensagens = lista

        // atualiza apenas o modificado - RECOMENDADO
        notifyItemInserted(listaMensagens.size)
        // atualiza toda a lista - NÃO RECOMENDADO
        //notifyDataSetChanged()
    }

    // editar item da lista
    fun editarListaDados(lista: MutableList<Mensagem>){
        listaMensagens[0] = Mensagem("Maria", "Boa tarde", "25/05/2023")
        //Toast.makeText(this, "Item editado com sucesso.", Toast.LENGTH_SHORT).show()
        notifyItemChanged(0)
    }

    // remover item da lista
    fun excluirItemListaDados(Lista: MutableList<Mensagem>){
        listaMensagens.removeAt(1)

        notifyItemRemoved(1)
    }



    inner class MensagemViewHolder(
        val itemView: View
    ) : ViewHolder(itemView){
        // elementos da tela
        //val textNome: TextView = itemView.findViewById(R.id.text_nome_teste)
        //val textDescricao: TextView = itemView.findViewById(R.id.text_descricao_teste)
        //val textData: TextView = itemView.findViewById(R.id.text_data_teste)
        //val imagemPerfil: ImageView = itemView.findViewById(R.id.image_perfil_teste)
        val imagemPerfil: ImageView = itemView.findViewById(R.id.imageCardProduto)
        //val retorno: ItemView = itemView.findViewById(R.id.ver_perfil)

        // funcao para executar e exibir eventos na tela
        fun bind(mensagem: Mensagem){
            //textNome.text = mensagem.nome
            //textDescricao.text = mensagem.descricao
            //textData.text = mensagem.data

            // Aplicando eventos de clique
            //val context = imagemPerfil.context
            imagemPerfil.setOnClickListener {
                clique(mensagem.nome)
                //Toast.makeText(context, "Deu certo, valeu ${mensagem.nome}", Toast.LENGTH_SHORT).show()
            }
        }

        fun editarPerfil(){

        }


    }

    // Ao criar ViewHolder - cria a visualização
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_card_view, parent, false
        )
        return MensagemViewHolder(itemView)
    }

    // ao vincular os dados para a visualização ( view holder)
    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        val mensagem = listaMensagens[position]
        holder.bind(mensagem)
    }

    // Recupera a quantidade de itens
    override fun getItemCount(): Int {
        return listaMensagens.size
    }

    fun confirmarExclusao(view: View?) {
        /*val alertBuider = AlertDialog.Builder(this)
        alertBuider.setTitle("Confirmar exclusão do item!")
        alertBuider.setMessage("Tem certeza disso?")
        alertBuider.setNegativeButton("cancelar"){dialog, posicao ->
            //Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show()
            //dialog.cancel()
        }
        alertBuider.setPositiveButton("Excluir"){dialog, posicao ->
            //Toast.makeText(this, "Item excluido", Toast.LENGTH_SHORT).show()
            //dialog.cancel()
        }*/

    }
}