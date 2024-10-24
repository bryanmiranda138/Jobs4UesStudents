package com.unichamba1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unichamba1.Fragmentos.FragmentInicio
import com.unichamba1.Fragmentos.FragmentMisOfertas
import com.unichamba1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.net.Uri
import com.unichamba1.Fragmentos.FragmentJovenes
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import com.unichamba1.Fragmentos.FragmentChats
import com.unichamba1.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity(),FragmentInicio.OnVerOfertasClickListener , FragmentInicio.OnVerJovenesClickListener{

    private lateinit var binding: ActivityMain2Binding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()




        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.mi_menu4)
        val hamburgerButton = findViewById<ImageButton>(R.id.btnBack)
        hamburgerButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        verFragmentInicio()


        binding.BottonNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_Inicio -> {
                    verFragmentInicio()
                    true
                }

                R.id.Item_Filtrar -> {
                    verFragmentFiltrar()
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
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Item_sitio1 -> {
                    abrirSitioWeb("https://bryanmiranda138.github.io/Jobs4UesStudentsSite/index.html")
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
                R.id.Item_Iniciar_Sesion -> {
                    // Lanza la actividad OpcionesLogin
                    val intent = Intent(this, OpcionesLogin::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }



    }
    override fun onVerJovenesClicked() {
        binding.BottonNV?.selectedItemId = R.id.Item_Filtrar
        verFragmentFiltrar()
    }
    override fun onVerOfertasClicked() {
        binding.BottonNV?.selectedItemId = R.id.Item_Mis_Ofertas
        verFragmentMisOfertas()
    }





    private fun abrirSitioWeb(url: String = "") {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }



    private fun verFragmentInicio() {
        binding.TituloRL!!.text = "Inicio"
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentInicio")
        fragmentTransition.commit()

    }

    private fun verFragmentFiltrar() {
        binding.TituloRL!!.text = "Jovenes"
        val fragment = FragmentJovenes()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentFiltral")
        fragmentTransition.commit()
    }

    private fun verFragmentMisOfertas() {
        binding.TituloRL!!.text = "Ofertas"
        val fragment = FragmentMisOfertas()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentMisOfertas")
        fragmentTransition.commit()
    }

    private fun verFragmentChats() {
        binding.TituloRL!!.text = "Cuenta"
        val fragment = FragmentChats()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1!!.id, fragment, "FragmentCuenta")
        fragmentTransition.commit()
    }

}








private fun Any.replace(id: Int, fragment: FragmentInicio, tag: String) {
}

private fun Any.replace(id: Int, fragment: FragmentJovenes, tag: String) {
}

private fun Any.replace(id: Int, fragment: FragmentMisOfertas, tag: String) {
}

