package com.example.projetoapploja.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.projetoapploja.ProdutosNovosInsterface
import com.example.projetoapploja.R
import com.google.android.material.textfield.TextInputEditText


class CadastroProdutoTela2Fragment : Fragment(), ProdutosNovosInsterface {

    var listaCadastro = mutableMapOf(
        "marca" to "",
        "tipo" to "",
        "sexo" to "",
        "novidade" to "",
        "modelo" to ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_cadastro_produto_tela2, container, false)
        // pegar primeiros dados da lista do novo produto
        val args = this.arguments
        listaCadastro.put("marca", args?.get("marca").toString())
        listaCadastro.put("tipo", args?.get("tipo").toString())
        listaCadastro.put("sexo", args?.get("sexo").toString())

        val botaoVoltar : Button = view.findViewById(R.id.btn_tela_produto_2)
        val botaoCancelar : Button = view.findViewById(R.id.btnCadastroCancelar)
        val botaoConfirmar : Button = view.findViewById(R.id.btnAvancarCadastroFotos)

        // botoes de clique
        botaoVoltar.setOnClickListener {
            val fragment = CadastrarProdutoTela1Fragment()
            val transition = fragmentManager?.beginTransaction()
            transition?.replace(R.id.fl_cadastro, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }
        botaoCancelar.setOnClickListener {
            view.findViewById<RadioGroup>(R.id.switchNovidade).clearCheck()
            view.findViewById<TextInputEditText>(R.id.editTextCadastroModelo).text = null
        }
        botaoConfirmar.setOnClickListener {
            verificarNovidade()
            verificarModelo()
            atualizarListaCadastro()
            enviarCadastro()
        }
        return view
    }

    private fun enviarCadastro() {
        val bundle = Bundle()
        val cadastroMarca : String = listaCadastro.get("marca").toString()
        val cadastroTipo : String = listaCadastro.get("tipo").toString()
        val cadastroGenero : String = listaCadastro.get("sexo").toString()
        val cadastroNovidade : String = listaCadastro.get("novidade").toString()
        val cadastroModelo : String = listaCadastro.get("modelo").toString()
        bundle.putString("marca", cadastroMarca)
        bundle.putString("tipo", cadastroTipo)
        bundle.putString("sexo", cadastroGenero)
        bundle.putString("novidade", cadastroNovidade)
        bundle.putString("modelo", cadastroModelo)

        val minhaInterface: ProdutosNovosInsterface = activity as ProdutosNovosInsterface
        minhaInterface.transferirDadosNovoProduto(listaCadastro)
    }

    private fun verificarModelo() {
        val modelo = view?.findViewById<TextInputEditText>(R.id.editTextCadastroModelo)?.text.toString()
        if (modelo.isNotEmpty()){
            listaCadastro.put("modelo", modelo)
        }
    }

    private fun atualizarListaCadastro() {
        val ehNovidade : String = if (verificarNovidade()) "sim" else "nao"
        listaCadastro.put("novidade", ehNovidade)
    }

    private fun verificarNovidade() : Boolean{
        val idNovidade = view?.findViewById<Switch>(R.id.switchNovidade)?.isChecked
        if (idNovidade!!) true else false
        return idNovidade
    }

    override fun transferirDadosNovoProduto(mensagem: MutableMap<String, String>) {
        val mensagem = listaCadastro
    }


}