package com.example.projetoapploja

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoapploja.databinding.ActivityCadastroBinding


class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnEnviar.setOnClickListener {
            radioButtonTipoOculos()
            radioButtonGeneroOculos()
            spinnerSelecionarItem()
        }

        inicializarToolbar()

        spinnerMarca()


    }

    private fun spinnerSelecionarItem() {
        val marcaSelecionada = binding.spinnerMarcas.selectedItem
        val marcaPosicao = binding.spinnerMarcas.selectedItemPosition
        if (marcaPosicao == 0){
            Toast.makeText(this, "Selecione um item da lista", Toast.LENGTH_SHORT).show()
            binding.textMarca.text = "Selecione um item"
        }else{
            binding.textMarca.text = "Selecionado: $marcaSelecionada - pos: $marcaPosicao"
        }
    }

    private fun spinnerMarca() {
        val marcas = listOf(
            "Selecione uma marca", "RayBan",
            "Ana Rickman", "Okley", "Mormaii", "Generico"
        )
        binding.spinnerMarcas.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            marcas
        )

        binding.spinnerMarcas.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun radioButtonGeneroOculos() {
        val idItemSelecionado = binding.rgSexo.checkedRadioButtonId
        val itemSelecionado: String?
        when( idItemSelecionado){
            R.id.rbMasculino -> itemSelecionado = "Masculino"
            R.id.rbFeminino -> itemSelecionado = "Feminino"
            R.id.rbUnissex -> itemSelecionado = "Unissex"
            R.id.rbInfantil -> itemSelecionado = "Infantil"
            else -> itemSelecionado = "Nenhum item selecionado"
        }
        binding.textGenero.text = itemSelecionado

        // binding.rgSexo.clearCheck()  // limpar check-box
    }

    private fun radioButtonTipoOculos() {
        val tipoSelecionadoGrau = binding.rbOculosGrau.isChecked
        if ( tipoSelecionadoGrau){
            binding.textTipo.text = "Oculos de grau"
        }else{
            binding.textTipo.text = "Oculos de sol"
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}