package com.example.projetoapploja.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.projetoapploja.PesquisaActivity
import com.example.projetoapploja.R
import com.example.projetoapploja.databinding.FragmentPesquisaBinding


class PesquisaFragment : Fragment() {

    private var _binding: FragmentPesquisaBinding? = null
    private val binding get() = _binding
    private lateinit var btnPesquisar: Button
    private lateinit var btnVoltar: Button
    private lateinit var btnLimparPesquisa: Button
    private lateinit var textPesquisa: TextView
    private lateinit var editPesquisa: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pesquisa, container, false)

        btnPesquisar = view.findViewById(R.id.btn_pesquisa)
        btnVoltar = view.findViewById(R.id.btn_voltar)
        btnLimparPesquisa = view.findViewById(R.id.btnCancelar)
        textPesquisa = view.findViewById(R.id.texto_nome_pesquisado)
        editPesquisa = view.findViewById(R.id.edit_texto_pesquisado)


        view.findViewById<Button>(R.id.btn_pesquisa).setOnClickListener {
            textPesquisa.text = editPesquisa.text.toString()
            editPesquisa.text = ""
        }
        view.findViewById<Button>(R.id.btn_voltar).setOnClickListener {
            val intent = Intent(requireContext(), PesquisaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            editPesquisa.text = ""
        }


        return view
    }

    // testes de encerramento do fragment
/*    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(), "Fechou tudo", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        Toast.makeText(requireContext(), "Fechou tudo mesmo", Toast.LENGTH_SHORT).show()
    }*/




}