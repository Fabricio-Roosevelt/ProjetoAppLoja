package com.example.projetoapploja

import FEMININO
import INFANTIL
import MASCULINO
import OCULOS_DE_GRAU
import OCULOS_SOLAR
import UNISSEX
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projetoapploja.databinding.ActivityPesquisaBinding
import com.example.projetoapploja.fragments.PesquisaFragment


class PesquisaActivity : AppCompatActivity() {

    private val binding by lazy {
      ActivityPesquisaBinding.inflate(layoutInflater)
    }
    var listaPesquisa = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pesquisaFragment = PesquisaFragment()

        inicializarToolbar()
        spinnerExibicao()
        binding.btnPesquisaComFiltro.setOnClickListener {
            spinnerSelecionarItem()
            radioButtonTipoOculos()
            radioButtonGeneroOculos()
            enviarItensPesquisa()
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }
        binding.btnCancelar.setOnClickListener {
            binding.spinnerMarcas.setSelection(0)
            binding.rgTipoOculos.clearCheck()
            binding.rgSexo.clearCheck()
        }
        binding.btnPesquisaGenerica.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun enviarItensPesquisa() {
        Toast.makeText(this, "$listaPesquisa", Toast.LENGTH_LONG).show()
        listaPesquisa.clear()
    }

    private fun spinnerExibicao() {
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

    private fun spinnerSelecionarItem() {
        val marcaSelecionada = binding.spinnerMarcas.selectedItem
        val marcaPosicao = binding.spinnerMarcas.selectedItemPosition
        if (marcaPosicao != 0){
            listaPesquisa.add(marcaSelecionada.toString())
        }
        binding.spinnerMarcas.setSelection(0)
    }

    private fun radioButtonGeneroOculos() {
        val idItemSelecionado = binding.rgSexo.checkedRadioButtonId
        val itemSelecionado: String?
        when( idItemSelecionado){
            R.id.rbMasculino -> itemSelecionado = MASCULINO
            R.id.rbFeminino -> itemSelecionado = FEMININO
            R.id.rbUnissex -> itemSelecionado = UNISSEX
            R.id.rbInfantil -> itemSelecionado = INFANTIL
            else -> itemSelecionado = null
        }
        if (itemSelecionado != null) {
            listaPesquisa.add(itemSelecionado)
        }
        binding.rgSexo.clearCheck()  // limpar check-box

    }

    private fun radioButtonTipoOculos() {
        val idTipoProdutoSelecionado = binding.rgTipoOculos.checkedRadioButtonId
        val tipoProdutoSelecionado: String?
        when(idTipoProdutoSelecionado){
            R.id.rbOculosGrau -> tipoProdutoSelecionado = OCULOS_DE_GRAU
            R.id.rbOculosSolar -> tipoProdutoSelecionado = OCULOS_SOLAR
            else -> tipoProdutoSelecionado = null
        }
        if (tipoProdutoSelecionado != null) {
            listaPesquisa.add(tipoProdutoSelecionado)
        }
        binding.rgTipoOculos.clearCheck()
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolBar.tbPrincipal
        setSupportActionBar(toolbar)
        binding.includeToolBar.tbPrincipal.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeToolBar.tbPrincipal.overflowIcon.apply {
            getColor(R.color.white)
        }
    }
}