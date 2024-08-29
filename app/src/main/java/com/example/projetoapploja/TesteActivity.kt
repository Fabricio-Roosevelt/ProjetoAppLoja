package com.example.projetoapploja

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projetoapploja.databinding.ActivityTesteBinding


class TesteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTesteBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()

    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        binding.includeToolbar.tbPrincipal.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeToolbar.tbPrincipal.overflowIcon.apply {
            getColor(R.color.white)
        }

        /*supportActionBar?.apply {
            title = "Cadastre um produto"
            setDisplayHomeAsUpEnabled(true)
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_teste, menu)
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
}