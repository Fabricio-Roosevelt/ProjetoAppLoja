package com.example.projetoapploja

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projetoapploja.databinding.ActivityMainBinding
import com.example.projetoapploja.fragments.AdicaoItemFragment
import com.example.projetoapploja.fragments.EdicaoItemFragment
import com.example.projetoapploja.fragments.PesquisaFragment


class MainActivity : AppCompatActivity(), MinhaInterface {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    ////////////////////////
    lateinit var textoVindoDoFragment: TextView
    lateinit var fragmento: Fragment
    //////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()
        //abrirFragmentPesquisa()
        //abrirFragmentContatos()

        /////////////////////////
        textoVindoDoFragment = findViewById(R.id.textRetornoFragment)
        abrirFragmentPesquisa(PesquisaFragment())
        /////////////////////////
    }

    private fun abrirFragmentPesquisa(fragment: Fragment) {
        val fragmentTransation = supportFragmentManager.beginTransaction()
        fragmentTransation.replace(R.id.fragment_conteudo, fragment)
        fragmentTransation.commit()
    }

    private fun abrirFragmentContatos() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, AdicaoItemFragment())
            .commit()
    }

    private fun abrirFragmentPesquisa() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, PesquisaFragment())
            .commit()
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeActionbar.tbPrincipal
        setSupportActionBar(toolbar)
        binding.includeActionbar.tbPrincipal.setTitleTextColor(
            ContextCompat.getColor(this,R.color.white)
        )
        binding.includeActionbar.tbPrincipal.overflowIcon.apply {
            getColor(R.color.white)
        }
    }

    private fun abrirTelaAdicaoItem() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, AdicaoItemFragment())
            .commit()
    }


    ///testes
    override fun transferirMensagem(msg: String) {
        textoVindoDoFragment.text = msg
        Log.i("saida", "$msg")
        if (msg.isNotEmpty()){
            if (msg == "Adicao"){
                abrirTelaAdicaoItem()
            } else if (msg == "Pesquisa"){
                abrirFragmentPesquisa()
            }
        } else {
            abrirFragmentContatos()
        }
    }

    // rascunho
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
     }
    private fun abrirTelaEdicaoItem() {
        // inicializar Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_conteudo, EdicaoItemFragment())
            .commit()
    }*/

}