package com.example.projetoapploja.fragments

import android.graphics.Bitmap
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
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toIcon
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetoapploja.ImagemAdapter
import com.example.projetoapploja.Imagens
import com.example.projetoapploja.Mensagem
import com.example.projetoapploja.MensagemAdapter
import com.example.projetoapploja.R
import com.example.projetoapploja.RecycleViewImagens
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.net.URL
import java.time.LocalDateTime
import java.util.Calendar

class AdicionarFotosFragment() : Fragment() {

    private val armazenamento by lazy {
        FirebaseStorage.getInstance()
    }

    private lateinit var imagemSelecionada: ImageView
    private lateinit var capturarCamera: ImageButton
    private lateinit var capturarGaleria: ImageButton
    private lateinit var botaoAPagar: Button
    private lateinit var botaoConfirmarFoto: Button
    private lateinit var botaoUpload: Button
    private lateinit var imagemProduto1: ImageView
    private lateinit var imagemProduto2: ImageView

    //// introduzir adapter
    private var gridLayoutManager: CardView? = null
    private lateinit var recycleViewImagens: ImagemAdapter
    private lateinit var recycleImagem: ImagemAdapter
    var listaImagens: MutableList<Imagens> = mutableListOf()

    private lateinit var adapter: ImagemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var novoArrayList: ArrayList<Imagens>
    private lateinit var imageId: Array<Int>
    private lateinit var mensagemAdapter: MensagemAdapter
    //////////////////////

    private var uriImagemSelecionada: Uri? = null
    private var idPasta: String? = null
    private var nomeFoto: String? = null
    private val abrirGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri ->
        if (uri != null){
            view?.findViewById<ImageView>(R.id.imagemSelecionada)?.setImageURI(uri)
            uriImagemSelecionada = uri
            Toast.makeText(requireContext(), "Imagem selecionada.", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(requireContext(), "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT).show()
            uriImagemSelecionada = null
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // capturar idProduto para ser o nome da pasta para salvar fotos
        idPasta = arguments?.getString("idPastaDeFotos")
        Log.i("saida", "Fragmento -- $idPasta")


        val lista = mutableListOf<Mensagem>(
            Mensagem("Fabricio", "Oi", "25/05/2022"),
            Mensagem("José", "Oi tudo bem?", "25/08/2022"),
            //Mensagem("Maria", "Boa tarde", "25/05/2023"),
            //Mensagem("Pedro", "Boa noite", "15/08/2023")
        )


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adicionar_fotos, container, false)
        capturarGaleria = view.findViewById(R.id.imagemGaleria)
        capturarGaleria.setOnClickListener {
            abrirGaleria.launch("image/*")
        }



        ////////////

        botaoUpload = view.findViewById<Button?>(R.id.btnUploadImagem)
        botaoUpload.setOnClickListener {
            uploadGaleria()
            //nomearArquivo()
        }

        botaoAPagar = view.findViewById(R.id.btnApagarFoto)
        botaoAPagar.setOnClickListener {
            view.findViewById<ImageView>(R.id.imagemSelecionada).setImageURI(null)
            uriImagemSelecionada = null
        }

        botaoConfirmarFoto = view.findViewById(R.id.btnConfirmarFoto)
        botaoConfirmarFoto.setOnClickListener {
            imagemProduto1 = view.findViewById(R.id.imageProduto1)
            imagemProduto1.setImageURI(uriImagemSelecionada)
        }
        imagemProduto2 = view.findViewById(R.id.imageProduto2)


        ////////////////// Recycle view
        /*val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rv_tela_imagens)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ImagemAdapter(novoArrayList)
        recyclerView.adapter = adapter*/
        //////////////////////


        return view
    }

    private fun visualizarImagensGravadas(url: Uri) {
        Picasso.get()
            .load(url)
            .into(imagemProduto2)
        //listaImagens.add(imagemProduto2)
        val caminho = armazenamento.getReference("produtos")
            .child(idPasta.toString())


    }


    private fun eventosClique() {
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
    }

    private fun apagarImagem() {
        Log.i("saida","Estou em apagar fotos.")
    }

    private fun cadastrarImagem() {
        Log.i("saida","Estou em CADASTRAR fotos.")
    }

    private fun uploadGaleria() {
        Log.i("saida","$uriImagemSelecionada")
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
        }
    }

    private fun nomearArquivo(): String {
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
        Log.i("saida","current -- $current")
        val dataAtual = current.toString()
            .replace(":","")
            .replace("-","")
            .replace("T","_")
        Log.i("saida","data -- $dataAtual")
        var nomeArquivo = "foto_$dataAtual.jpg"
        return nomeArquivo
    }
}