package com.example.projetoapploja

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projetoapploja.databinding.ActivityPesquisaBinding


class PesquisaActivity : AppCompatActivity() {

    private val binding by lazy {
      ActivityPesquisaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()
        spinnerExibicao()
        binding.btnPesquisaComFiltro.setOnClickListener {
            radioButtonTipoOculos()
            radioButtonGeneroOculos()
            spinnerSelecionarItem()
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
        Toast.makeText(this, "$itemSelecionado", Toast.LENGTH_SHORT).show()
        // binding.rgSexo.clearCheck()  // limpar check-box
    }

    private fun radioButtonTipoOculos() {
        val tipoSelecionadoGrau = binding.rbOculosGrau.isChecked
        if ( tipoSelecionadoGrau){
            Toast.makeText(this, "Oculos de grau", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Oculos de sol", Toast.LENGTH_SHORT).show()
        }
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
        if (marcaPosicao == 0){
            Toast.makeText(this, "Selecione um item da lista", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "$marcaSelecionada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolBar.tbAlternativa
        setSupportActionBar(toolbar)
        binding.includeToolBar.tbAlternativa.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeToolBar.tbAlternativa.overflowIcon.apply {
            getColor(R.color.white)
        }
    }
}