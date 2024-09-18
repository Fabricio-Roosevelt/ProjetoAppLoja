package com.example.projetoapploja.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import com.example.projetoapploja.R


class TesteFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_teste, container, false)

        view.findViewById<Button>(R.id.btn_tela_teste).setOnClickListener {
            enviarRetorno()
        }


        return view
    }

    fun enviarRetorno() : String{
        val retorno = view?.findViewById<TextView>(R.id.text_tela_teste).toString()
        return retorno
    }

}