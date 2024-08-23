package com.example.projetoapploja

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetoapploja.databinding.ActivityLoginBinding
import com.example.projetoapploja.databinding.ActivityMainBinding
import com.example.projetoapploja.fragments.AdicaoItemFragment
import com.example.projetoapploja.fragments.EdicaoItemFragment
import com.example.projetoapploja.fragments.PesquisaFragment
import com.example.projetoapploja.fragments.Tela1CadastroFragment
import com.example.projetoapploja.fragments.TelaOpoesFragment
import org.checkerframework.checker.units.qual.A

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()
        abrirFragmentPesquisa()

    }

    private fun abrirFragmentPesquisa() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, PesquisaFragment())
            .commit()
    }


    private fun inicializarToolbar() {
        val toolbar = binding.includeActionbar.tbAlternativa
        setSupportActionBar(toolbar)
        binding.includeActionbar.tbAlternativa.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeActionbar.tbAlternativa.overflowIcon.apply {
            getColor(R.color.white)
        }
    }

   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        supportActionBar?.apply {
            title = "Pesquisar"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.includeActionbar.tbAlternativa.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.itemAdicionar -> {
                    abrirTelaAdicaoItem()
                    return@setOnMenuItemClickListener true
                }
                R.id.itemEditar -> {
                    abrirTelaEdicaoItem()
                    return@setOnMenuItemClickListener true
                }
                R.id.itemPesquisar -> {
                    abrirTelaCadastro()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener true
                }
            }
        }
        return true
    }*/

    private fun abrirTelaAdicaoItem() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, AdicaoItemFragment())
            .commit()
    }

    private fun abrirTelaEdicaoItem() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, EdicaoItemFragment())
            .commit()
    }

    private fun abrirTelaCadastro() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, Tela1CadastroFragment())
            .commit()
    }
}