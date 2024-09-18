package com.example.projetoapploja.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projetoapploja.MinhaInterface
import com.example.projetoapploja.R

class EdicaoItemFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edicao_item, container, false)

        val editNome = view.findViewById<EditText>(R.id.editTextInputEdicao)
        val btn = view.findViewById<Button>(R.id.btnEdicao)

        val minhaInterface: MinhaInterface = activity as MinhaInterface

        btn.setOnClickListener {
            val msg = editNome.text.toString()
            minhaInterface.transferirMensagem(msg)
        }

        return view
    }

}