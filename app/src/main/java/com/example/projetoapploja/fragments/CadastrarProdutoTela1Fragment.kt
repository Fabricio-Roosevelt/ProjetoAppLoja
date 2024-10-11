package com.example.projetoapploja.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projetoapploja.R

class CadastrarProdutoTela1Fragment : Fragment() {

    lateinit var btnAvancar : Button
    var listaCadastro = mutableMapOf("marca" to "", "tipo" to "", "sexo" to "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cadastrar_produto_tela1, container, false)
        // Build array data to spinner
        val produtos = resources.getStringArray(R.array.marcas)
        val spinner = view.findViewById<Spinner>(R.id.spinnerMarcas)
        if (spinner != null){
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, produtos)
            spinner.adapter = adapter
        }

        btnAvancar = view.findViewById(R.id.btn_avancar_tela2)
        btnAvancar.setOnClickListener {
            verificarTipoProduto()
            verificarGeneroProduto()
            verificarMarcaProduto()
            verificarDadosInseridos()
        }
        return view
    }

    private fun verificarDadosInseridos(){
        if (verificarMarcaProduto() && verificarGeneroProduto() && verificarTipoProduto()){
            enviarDadosCapturados()
            // limpar dados
            view?.findViewById<Spinner>(R.id.spinnerMarcas)?.setSelection(0)
            view?.findViewById<RadioGroup>(R.id.rgSexo)?.clearCheck()
            view?.findViewById<RadioGroup>(R.id.rgTipoOculos)?.clearCheck()
        }
    }

    private fun enviarDadosCapturados() {
        val bundle = Bundle()
        val marcaEscolhida : String = listaCadastro.get("marca").toString()
        val tipoOculos : String = listaCadastro.get("tipo").toString()
        val generoOculos : String = listaCadastro.get("sexo").toString()
        bundle.putString("marca", marcaEscolhida)
        bundle.putString("tipo", tipoOculos)
        bundle.putString("sexo", generoOculos)

        val fragment = CadastroProdutoTela2Fragment()
        fragment.arguments = bundle
        val transition = fragmentManager?.beginTransaction()
        transition?.replace(R.id.fl_cadastro, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun verificarMarcaProduto() : Boolean{
        val marcaSelecionada = view?.findViewById<Spinner>(R.id.spinnerMarcas)?.selectedItem.toString()
        val marcaPosicao = view?.findViewById<Spinner>(R.id.spinnerMarcas)?.selectedItemPosition
        if (marcaPosicao != 0) {
            listaCadastro.put("marca", marcaSelecionada)
            return true
        } else{
            Toast.makeText(requireContext(),
                "Escolha uma marca.\nSe não tiver, escolha outras.", Toast.LENGTH_SHORT).show()
        }
        view?.findViewById<Spinner>(R.id.spinnerMarcas)?.setSelection(0)
        return false
    }

    private fun verificarGeneroProduto() : Boolean{
        val idItemSelecionado = view?.findViewById<RadioGroup>(R.id.rgSexo)?.checkedRadioButtonId
        val itemSelecionado: String?
        if (idItemSelecionado == R.id.rbMasculino) {
            itemSelecionado = "Masculino"
            listaCadastro.put("sexo", itemSelecionado)
            return true
        }
        else if (idItemSelecionado == R.id.rbFeminino) {
            itemSelecionado = "Feminino"
            listaCadastro.put("sexo", itemSelecionado)
            return true
        }
        else if (idItemSelecionado == R.id.rbUnissex) {
            itemSelecionado = "Unissex"
            listaCadastro.put("sexo", itemSelecionado)
            return true
        }
        else if (idItemSelecionado == R.id.rbInfantil) {
            itemSelecionado = "Infantil"
            listaCadastro.put("sexo", itemSelecionado)
            return true
        }
        else {
            itemSelecionado = "Nenhum item selecionado"
            Toast.makeText(requireContext(),
                "Escolha um genero para o óculos.", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun verificarTipoProduto() : Boolean{
        val idtipoSelecionadoGrau = view?.findViewById<RadioGroup>(R.id.rgTipoOculos)?.checkedRadioButtonId
        val tipoSelecionadoGrau: String?
        when( idtipoSelecionadoGrau) {
            R.id.rbOculosGrau -> {
                tipoSelecionadoGrau = "Oculus de grau"
                listaCadastro.put("tipo", tipoSelecionadoGrau)
                return true
            }
            R.id.rbOculosSolar -> {
                tipoSelecionadoGrau = "Oculos solar"
                listaCadastro.put("tipo", tipoSelecionadoGrau)
                return true
            }
            else -> {
                Toast.makeText(requireContext(),
                    "Escolha um tipo de oculos.", Toast.LENGTH_SHORT).show()
                return false
            }
        }
    }
}