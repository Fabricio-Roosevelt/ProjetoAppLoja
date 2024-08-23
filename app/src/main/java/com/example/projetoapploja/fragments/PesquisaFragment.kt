package com.example.projetoapploja.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.projetoapploja.R
import com.example.projetoapploja.databinding.ActivityPrimeiraTelaBinding
import com.example.projetoapploja.databinding.FragmentPesquisaBinding


class PesquisaFragment : Fragment() {

    private var _binding: FragmentPesquisaBinding? = null
    private val binding get() = _binding
    //private lateinit var textMarca: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pesquisa, container, false)
        return view
    }




}