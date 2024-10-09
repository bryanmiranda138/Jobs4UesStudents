package com.unichamba1.Fragmentos

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.unichamba1.R
import com.unichamba1.adapter.OfertaAdapter
import com.unichamba1.databinding.FragmentMisAnunciosPublicadosBinding
import com.unichamba1.model.Oferta

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Mis_Anuncios_Publicados_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMisAnunciosPublicadosBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var anunciosArrayList: ArrayList<Oferta>
    private lateinit var adapterAnuncios: OfertaAdapter

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMisAnunciosPublicadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        cargarMisAnuncios()
        progressDialog = ProgressDialog(mContext)
        progressDialog.setTitle("Cargando")
        progressDialog.setCanceledOnTouchOutside(false)

    }

    private fun cargarMisAnuncios() {
        // Inicializar la lista de anuncios
        anunciosArrayList = ArrayList()
        // Obtener el correo del usuario logueado
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        // Verificar que el correo del usuario logueado no sea nulo
        if (userEmail != null) {
            // Referencia a Firestore
            val ref = FirebaseFirestore.getInstance()

            // Consulta para obtener los anuncios filtrados por el correo del usuario actual
            ref.collection("anuncios")
                .whereEqualTo("quienPublica", userEmail) // Filtrar por el campo 'quienPublica'
                .get() // Realizar la consulta
                .addOnSuccessListener { result ->
                    anunciosArrayList.clear() // Limpiar la lista antes de agregar nuevos datos
                    for (document in result) {
                        try {
                            // Convertir cada documento en un objeto de tipo Oferta
                            val modeloAnuncio = document.toObject(Oferta::class.java)
                            if (modeloAnuncio != null) {
                                modeloAnuncio.id = document.id // Almacenar el id del documento en el modelo
                                // Agregar cada anuncio a la lista
                                anunciosArrayList.add(modeloAnuncio)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace() // Manejo de errores si algo falla en la conversión
                        }
                    }

                    // Configurar el adaptador después de cargar los datos
                    adapterAnuncios = OfertaAdapter(anunciosArrayList)
                    binding.misAnunciosRv.adapter = adapterAnuncios
                }
                .addOnFailureListener { exception ->
                    // Manejar el error si falla la consulta
                    exception.printStackTrace()
                }
        } else {
            // Manejar el caso en el que el usuario no esté logueado o no tenga un correo
            Toast.makeText(context, "No se pudo obtener el correo del usuario", Toast.LENGTH_SHORT).show()
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Mis_Anuncios_Publicados_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Mis_Anuncios_Publicados_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}