package com.example.projetoapploja.fragments

import MARCA
import MODELO
import NAO
import NOVIDADE
import SEXO
import SIM
import TIPO
import android.os.Bundle
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
        MARCA to "",
        TIPO to "",
        SEXO to "",
        NOVIDADE to "",
        MODELO to ""
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_cadastro_produto_tela2, container, false)
        // pegar primeiros dados da lista do novo produto
        val args = this.arguments
        listaCadastro.put(MARCA, args?.get(MARCA).toString())
        listaCadastro.put(TIPO, args?.get(TIPO).toString())
        listaCadastro.put(SEXO, args?.get(SEXO).toString())

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
        val cadastroMarca : String = listaCadastro.get(MARCA).toString()
        val cadastroTipo : String = listaCadastro.get(TIPO).toString()
        val cadastroGenero : String = listaCadastro.get(SEXO).toString()
        val cadastroNovidade : String = listaCadastro.get(NOVIDADE).toString()
        val cadastroModelo : String = listaCadastro.get(MODELO).toString()
        bundle.putString(MARCA, cadastroMarca)
        bundle.putString(TIPO, cadastroTipo)
        bundle.putString(SEXO, cadastroGenero)
        bundle.putString(NOVIDADE, cadastroNovidade)
        bundle.putString(MODELO, cadastroModelo)

        val minhaInterface: ProdutosNovosInsterface = activity as ProdutosNovosInsterface
        minhaInterface.transferirDadosNovoProduto(listaCadastro)
    }

    private fun verificarModelo() {
        val modelo = view?.findViewById<TextInputEditText>(R.id.editTextCadastroModelo)?.text.toString()
        if (modelo.isNotEmpty()){
            listaCadastro.put(MODELO, modelo)
        }
    }

    private fun atualizarListaCadastro() {
        val ehNovidade : String = if (verificarNovidade()) SIM else NAO
        listaCadastro.put(NOVIDADE, ehNovidade)
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