package com.example.projetoapploja

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projetoapploja.databinding.ActivityCadastrarProdutoBinding


class CadastrarProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarProdutoBinding.inflate(layoutInflater)
    }
    var listaCadastro = mutableMapOf("marca" to "", "tipo" to "", "sexo" to "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //supportActionBar?.hide()   // retirar actionbar da tela

        binding.btnPesquisa.setOnClickListener {
            spinnerCadastrarItem()
            radioButtonTipoOculos()
            radioButtonGeneroOculos()
        }
        inicializarToolbar()
        spinnerMarca()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_alternativo, menu)
        supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.includeToolbar.tbAlternativa.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId){
                R.id.itemPesquisar -> {
                    startActivity(Intent(this, PesquisaActivity::class.java))
                    return@setOnMenuItemClickListener true
                }
                R.id.itemEditar -> {
                    Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.itemSair -> {
                    startActivity(Intent(this, PrimeiraTelaActivity::class.java))
                    return@setOnMenuItemClickListener true
                }else -> {
                    return@setOnMenuItemClickListener true
                }
            }
        }
        return true
    }

    private fun spinnerCadastrarItem() {
        val marcaSelecionada = binding.spinnerMarcas.selectedItem.toString()
        val marcaPosicao = binding.spinnerMarcas.selectedItemPosition
        if (marcaPosicao != 0) {
            listaCadastro.put("marca", marcaSelecionada)
        }

        binding.spinnerMarcas.setSelection(0)
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
        listaCadastro.put("sexo", itemSelecionado)
        binding.textSaida.text = listaCadastro.toString()   // retirar no final
        binding.rgSexo.clearCheck()  // limpar check-box
    }

    private fun radioButtonTipoOculos() {
        val idtipoSelecionadoGrau = binding.rgTipoOculos.checkedRadioButtonId
        val tipoSelecionadoGrau: String?
        when( idtipoSelecionadoGrau) {
            R.id.rbOculosGrau -> tipoSelecionadoGrau = "Oculus de grau"
            R.id.rbOculosSolar -> tipoSelecionadoGrau = "Oculos solar"
            else -> tipoSelecionadoGrau = "Nenhum item selecionado"
        }
        listaCadastro.put("tipo", tipoSelecionadoGrau)
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