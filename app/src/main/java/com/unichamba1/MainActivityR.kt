package com.unichamba1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unichamba1.Fragmentos.FragmentCuenta
import com.unichamba1.Fragmentos.FragmentNuevaOferta
import com.google.firebase.auth.FirebaseAuth
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.unichamba1.Fragmentos.FragmentJovenes
import com.unichamba1.Fragmentos.FragmentMisOfertas
import com.unichamba1.Fragmentos.Mis_Anuncios_Publicados_Fragment
import com.unichamba1.databinding.ActivityMainRBinding


class MainActivityR : AppCompatActivity(){

    private lateinit var binding: ActivityMainRBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.mi_menu2)
        val hamburgerButton = findViewById<ImageButton>(R.id.btnBack)
        hamburgerButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        verFragmentNuevaOfertaR()


        binding.BottonNV!!.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_Cuenta -> {
                    verFragmentFiltrar()
                    true
                }

                R.id.Item_Publicar_Oferta-> {
                    comprobarSesion()
                    true
                }

                R.id.Item_Mis_Anuncios_Publicados -> {
                    verFragmentMisAnunciosPublicados()
                    true
                }

                R.id.Item_Mis_Ofertas -> {
                    verFragmentMisOfertas()
                    true
                }

                else -> {
                    false
                }
            }

        }
        // Verifica si se debe mostrar FragmentInicio
        if (intent.getBooleanExtra("mostrarFragmentNuevaOferta", false)) {
            comprobarSesion()
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Item_sitio1 -> {
                    abrirSitioWeb("https://bryanmiranda138.github.io/Jobs4UesStudentsSite/")
                    true
                }

                R.id.Item_Terminos1 -> {
                    abrirSitioWeb("https://bryanmiranda138.github.io/Jobs4UesStudentsSite/terminosycondiciones/")
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.Item_Quienes_Somos1 -> {
                    abrirSitioWeb("https://bryanmiranda138.github.io/Jobs4UesStudentsSite/quienessomos/")
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.Item_Cerrar_Sesion -> {
                    // Mostrar mensaje de despedida
                    Toast.makeText(this, "¡ Hasta luego !", Toast.LENGTH_SHORT).show()

                    // Cerrar la sesión del usuario con FirebaseAuth
                    FirebaseAuth.getInstance().signOut()

                    // Lanza la actividad OpcionesLogin
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpia el backstack para evitar que el usuario vuelva con el botón atrás
                    startActivity(intent)
                    // Cierra el DrawerLayout
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }

    }

    private fun comprobarSesion() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val user = firebaseAuth.currentUser

        if (user != null) {
            val userId = user.uid
            val termsAccepted = sharedPreferences.getBoolean(userId, false)

            if (!termsAccepted) {
                mostrarTérminosYCondiciones(sharedPreferences.edit(), userId)
            } else {
                // El usuario ya ha aceptado los términos y condiciones
                verFragmentNuevaOfertaR()
            }
        } else {
            // Si el usuario no ha iniciado sesión, redirigirlo a la pantalla de inicio de sesión
            startActivity(Intent(this, OpcionesLogin::class.java)) // Cierra todas las actividades previas
        }
    }

    private fun mostrarTérminosYCondiciones(sharedPreferencesEditor: SharedPreferences.Editor, userId: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_terms)
        val acceptButton = dialog.findViewById<Button>(R.id.accept_button)
        acceptButton.setOnClickListener {
            // Guardar el estado de aceptación en SharedPreferences
            sharedPreferencesEditor.putBoolean(userId, true)
            sharedPreferencesEditor.apply()
            dialog.dismiss() // Cerrar el diálogo
            verFragmentNuevaOfertaR() // Mostrar la pantalla principal después de aceptar los términos
        }
        dialog.show()
    }



    private fun verFragmentCuentaR() {
        binding.TituloRL!!.text = "Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentCuenta")
        fragmentTransition.commit()
    }

    private fun abrirSitioWeb(url: String = "") {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun verFragmentMisOfertas() {
        binding.TituloRL!!.text = "Ofertas"
        val fragment = FragmentMisOfertas()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentMisOfertas")
        fragmentTransition.commit()
    }

    private fun verFragmentNuevaOfertaR() {
        binding.TituloRL!!.text = "Publicar Oferta"
        val fragment = FragmentNuevaOferta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentNuevaOferta")
        fragmentTransition.commit()
    }

    private fun verFragmentFiltrar() {
        binding.TituloRL!!.text = "Jovenes"
        val fragment = FragmentJovenes()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentFiltral")
        fragmentTransition.commit()
    }

    private fun verFragmentMisAnunciosPublicados() {
        binding.TituloRL!!.text = "Mis Anuncios"
        val fragment = Mis_Anuncios_Publicados_Fragment()//FragmentMisOfertas()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentMisAnuncios")
        fragmentTransition.commit()
    }


}











private fun Any.replace(id: Int, fragment: FragmentCuenta, tag: String) {
}

private fun Any.replace(id: Int, fragment: FragmentNuevaOferta, tag: String) {
}