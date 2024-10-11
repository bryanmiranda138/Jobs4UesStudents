package com.unichamba1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unichamba1.JovenesDetalleActivity.Companion.EXTRA_ID
import com.unichamba1.adapter.OfertaAdapter
import com.unichamba1.model.Oferta
import java.net.URLEncoder

class OfertaDetalleActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnApply: Button
    private lateinit var quienPublicaText: String // Variable para almacenar el correo del publicador
    private lateinit var btnEliminarAnuncio: Button

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_quienPublica = "extra_quienPublica"
        const val EXTRA_TELEFONO = "extra_telefono"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_CARRERA = "extra_carrera"
        const val EXTRA_IMAGEN = "extra_imagen"
        const val EXTRA_DIRECTION = "extra_direction"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oferta_detalle)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Detalles de oferta"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)

        // Inicialización y configuración del botón
        btnEliminarAnuncio = findViewById(R.id.btnEliminarAnuncio)
        btnEliminarAnuncio.setOnClickListener {
            eliminarAnuncio()
        }

        // Inicializa Firebase Authentication
        mAuth = FirebaseAuth.getInstance()

        // Obtén una referencia al botón "Aplicar"
        btnApply = findViewById(R.id.btnApply)
        val userEmail = mAuth.currentUser?.email
        // Verifica si el usuario está autenticado
        if (mAuth.currentUser != null && esEstudiante(userEmail)==true) {
            // Si el usuario está autenticado, habilita el botón "Aplicar"
            btnApply.isEnabled = true
        } else {
            Toast.makeText(this, "Debes ser estudiante para aplicar a esta oferta!", Toast.LENGTH_SHORT).show()
            btnApply.isEnabled = false
        }

        // Obtén el correo del publicador de la oferta
        val quienPublicaEmail = intent.getStringExtra(EXTRA_quienPublica) ?: ""

        // Comprueba si el usuario logueado es distinto al publicador de la oferta
        if (userEmail != quienPublicaEmail) {
            // Si es distinto, oculta el botón de eliminar anuncio
            btnEliminarAnuncio.visibility = View.GONE
        } else {
            // Si coincide, asegúrate de mostrar el botón
            btnEliminarAnuncio.visibility = View.VISIBLE
        }


        val description: TextView = findViewById(R.id.description)
        val quienPublica: TextView = findViewById(R.id.quienPublica)
        val carrera: TextView = findViewById(R.id.carrera)
        val image: ImageView = findViewById(R.id.imagen)

        val direction: TextView = findViewById(R.id.direction)
        val municipio = intent.getStringExtra(EXTRA_DIRECTION)
        direction.text = municipio

        // Habilitar el botón de retroceso en el Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val intent = intent
        description.text = intent.getStringExtra(EXTRA_DESCRIPTION)
        quienPublicaText = intent.getStringExtra(EXTRA_quienPublica) ?: ""
        quienPublica.text = quienPublicaText
        val carrerasArray = intent.getStringArrayExtra(EXTRA_CARRERA) ?: emptyArray()
        val carrerasList = ArrayList(carrerasArray.toList())
        val carrerasText = carrerasList.joinToString(", ")
        carrera.text = carrerasText

        // Cargar la imagen usando Glide
        val imagenUrl = intent.getStringExtra(EXTRA_IMAGEN)
        Glide.with(this)
            .load(imagenUrl)
            .placeholder(R.drawable.ic_cuenta) // Placeholder mientras se carga la imagen
            .error(R.drawable.barra) // Imagen de error si falla la carga
            .into(image) // ImageView donde se carga la imagen

        // Configurar onClickListener para el botón "Aplicar"
        btnApply.setOnClickListener {
            openWhatsApp()
        }
    }


    private fun esEstudiante(email: String?): Boolean {
        return email?.let {
            it.endsWith("@ues.edu.sv") && !it.substringBefore("@ues.edu.sv").contains(".")
        } ?: false
    }

    private fun eliminarAnuncio() {
        // Obtener el ID del anuncio que se desea eliminar
        val anuncioId = intent.getStringExtra(EXTRA_ID) // Asegúrate de que estás pasando el ID del anuncio como un extra en el Intent

        // Referencia a Firestore
        val db = FirebaseFirestore.getInstance()

        // Eliminar el documento/anuncio
        anuncioId?.let {
            db.collection("anuncios").document(it)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Anuncio eliminado con éxito", Toast.LENGTH_SHORT).show()
                    // Redirigir o finalizar la actividad según sea necesario
                    finish() // Cierra la actividad actual y regresa a la anterior
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al eliminar el anuncio: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }


   private fun openWhatsApp() {

        val phoneNumber = intent.getStringExtra(EXTRA_TELEFONO) // Reemplaza con el número de teléfono al que deseas enviar el mensaje
       val message = "Hola, estoy interesado en aplicar a la oferta" // Mensaje predeterminado

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$phoneNumber/?text=${URLEncoder.encode(message, "UTF-8")}")
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
