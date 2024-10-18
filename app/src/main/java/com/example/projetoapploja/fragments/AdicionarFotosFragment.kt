package com.example.projetoapploja.fragments

import FOTOS
import ID_PASTA_FOTOS
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoapploja.ImagemAdapter
import com.example.projetoapploja.R
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.util.Calendar
import java.util.UUID

class AdicionarFotosFragment() : Fragment() {

    private val armazenamento by lazy {
        FirebaseStorage.getInstance()
    }
    private lateinit var capturarGaleria: ImageButton
    private lateinit var botaoAPagar: Button
    private lateinit var botaoConfirmarFoto: Button
    private lateinit var botaoUpload: Button

    private var uriImagemSelecionada: Uri? = null
    private lateinit var uriModificada: Uri
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagemAdapter: ImagemAdapter
    private val imagemUris = mutableListOf<Uri>()
    private var idPasta: String? = null
    private val abrirGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null){
            view?.findViewById<ImageView>(R.id.imagemSelecionada)?.setImageURI(uri)
            Toast.makeText(requireContext(), "Imagem selecionada.", Toast.LENGTH_SHORT).show()
            uri?.let {
                uriModificada = uri
            }
        }else{
            Toast.makeText(requireContext(), "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT).show()
        }
    }

    ////// implantar interface
    interface OnDataPass {
        fun onDataPass(data: MutableList<String>)
    }
    private lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataPasser = context as OnDataPass
        }catch (e: ClassCastException){
            throw ClassCastException("$context deve implementar OndataPass")
        }
    }
    //////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // capturar idProduto para ser o nome da pasta para salvar fotos
        idPasta = arguments?.getString(ID_PASTA_FOTOS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_adicionar_fotos, container, false)

        recyclerView = view.findViewById(R.id.rv_tela_imagens)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
        imagemAdapter = ImagemAdapter(imagemUris)
        recyclerView.adapter = imagemAdapter

        capturarGaleria = view.findViewById(R.id.imagemGaleria)
        capturarGaleria.setOnClickListener {
            abrirGaleria.launch("image/*")
        }

        botaoAPagar = view.findViewById(R.id.btnApagarFoto)
        botaoAPagar.setOnClickListener {
            view.findViewById<ImageView>(R.id.imagemSelecionada).setImageURI(null)
            uriImagemSelecionada = null

        }

        botaoConfirmarFoto = view.findViewById(R.id.btnConfirmarFoto)
        botaoConfirmarFoto.setOnClickListener {
            imagemUris.add(uriModificada)
            imagemAdapter.notifyItemInserted(imagemUris.size - 1)
        }

        botaoUpload = view.findViewById(R.id.btnUploadImagem)
        botaoUpload.setOnClickListener {
            uploadGaleria()
            uriImagemSelecionada = null
        }

        return view
    }


    private fun uploadGaleria() {
        val imagemUrls = mutableListOf<String>()
        var contador = 0
        imagemUris.forEach{ uri ->
            val fileName = UUID.randomUUID().toString() + ".jpg"
            //val fileName = nomearArquivo()
            val imageRef = armazenamento
                .getReference(FOTOS)
                .child("$idPasta/$fileName")
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        imagemUrls.add(downloadUrl.toString())
                        contador++
                        if (contador == imagemUris.size){
                            salvarNoStorage(imagemUrls)
                            //Toast.makeText(requireContext(), "Secesso ao fazer up-load da imagem", Toast.LENGTH_SHORT).show()
                            activity?.supportFragmentManager
                                ?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        }
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Erro ao fazer upload: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    ////////////// interface
    private fun passDataToActivity(data: MutableList<String>){
        dataPasser.onDataPass(data)
    }

    private fun salvarNoStorage(imagemUrls: MutableList<String>) {
        val imagesMap = hashMapOf(
            "images" to imagemUrls
        )
        ///////////////// interface
        passDataToActivity(imagemUrls)
        ///////////////////
    }
    //////////////////////////////////


    //// Rascunho
    /*private fun nomearArquivo(): String {
        val calendar = Calendar.getInstance()
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                )
        } else {
            Log.i("saida","Versão errada!!!!")
        }
        val dataAtual = current.toString()
            .replace(":","")
            .replace("-","")
            .replace("T","_")
        var nomeArquivo = "imagem_$dataAtual.jpg"
        return nomeArquivo
    }*/

    /*private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            view?.findViewById<ImageView>(R.id.imagemSelecionada)?.setImageURI(uri)
            uriImagemSelecionada = uri
            uriModificada = uriImagemSelecionada.toString()
            Log.i("saida", "$uriModificada")
            Toast.makeText(requireContext(), "Imagem selecionada.", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(requireContext(), "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT)
                .show()
            uriImagemSelecionada = null
        }
    }*/

    /*
       if (uriImagemSelecionada != null) {
           armazenamento
               .getReference("fotos")
               .child(idPasta.toString())
               .child(nomearArquivo())
               .putFile(uriImagemSelecionada!!)
               .addOnSuccessListener { task ->
                   Toast.makeText(requireContext(), "Secesso ao fazer up-load da imagem", Toast.LENGTH_SHORT).show()
                   task.metadata?.reference?.downloadUrl
                       ?.addOnSuccessListener { urlFirebase ->
                       Toast.makeText(requireContext(), "$urlFirebase", Toast.LENGTH_SHORT).show()
                           visualizarImagensGravadas(urlFirebase)
                       }
               }.addOnFailureListener{erro ->
                   Toast.makeText(requireContext(), "Erro ao fazer up-load da imagem", Toast.LENGTH_SHORT).show()
               }
       }else{
           Toast.makeText(requireContext(), "Não há imagem selecionada.", Toast.LENGTH_SHORT).show()
           Log.i("saida", "Não há imagem selecionada.")
       }*/

    /*private fun eventosClique() {
        var opcao: Int = 0
        when (id) {
            (R.id.btnConfirmarFoto) -> {
                opcao = 1
                cadastrarImagem()
                Log.i("saida","Confirmar - $id")
            }
            (R.id.btnApagarFoto) -> {
                opcao = 2
                apagarImagem()
                Log.i("saida","Apagar -- $id")
            }
            (R.id.imagemGaleria) -> {
                opcao = 3
                Log.i("saida","Caturar Imagem -- $id")
            }
        }
        Log.i("saida","$opcao")
    }*/
}