package com.example.projetoapploja

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projetoapploja.databinding.ActivityCadastroBinding


class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //supportActionBar?.hide()   // retirar actionbar da tela

        binding.btnPesquisaComFiltro.setOnClickListener {
            radioButtonTipoOculos()
            radioButtonGeneroOculos()
            spinnerSelecionarItem()
        }

        inicializarToolbar()
        spinnerMarca()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_principal, menu)
        supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }
        menuInflater.inflate(R.menu.menu_alternativo, menu)
        supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }

        /*binding.includeToolbar.tbAlternativa.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId){
                R.id.itemPesquisar -> {
                    Toast.makeText(this, "Oesquisar", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.itemAdicionar -> {
                    Toast.makeText(this, "Adicionar", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.itemEditar -> {
                    Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.itemSair -> {
                    Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }else -> {
                    return@setOnMenuItemClickListener true
                }
            }
        }*/

        return true


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
        val toolbar = binding.includeToolbar.tbAlternativa
        setSupportActionBar(toolbar)
        binding.includeToolbar.tbAlternativa.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeToolbar.tbAlternativa.overflowIcon.apply {
            getColor(R.color.white)
        }

        /*supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }*/
    }
}