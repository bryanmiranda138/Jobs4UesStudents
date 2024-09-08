package com.unichamba1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unichamba1.Fragmentos.FragmentCuenta
import com.unichamba1.Fragmentos.FragmentInicio
import com.unichamba1.Fragmentos.FragmentMisOfertas
import com.unichamba1.Fragmentos.FragmentNuevaOferta
import com.unichamba1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.app.Dialog
import android.net.Uri
import android.view.ViewGroup
import android.widget.Button
import com.unichamba1.Fragmentos.FragmentJovenes
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.ImageButton
import androidx.core.view.GravityCompat


class MainActivity2 : AppCompatActivity(),FragmentInicio.OnVerOfertasClickListener , FragmentInicio.OnVerJovenesClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.mi_menu0)
        val hamburgerButton = findViewById<ImageButton>(R.id.btnBack)
        hamburgerButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        verFragmentInicio()


        binding.BottonNV!!.setOnItemSelectedListener { item ->
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
                R.id.Item_sitio -> {
                    abrirSitioWeb("https://www.unichamba.com/")
                    true
                }


                R.id.Item_Terminos -> {
                    abrirSitioWeb("https://website-unichamba.netlify.app/policy")
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.Item_Quienes_Somos -> {
                    abrirSitioWeb("https://website-unichamba.netlify.app/details")
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

}








private fun Any.replace(id: Int, fragment: FragmentInicio, tag: String) {
}

private fun Any.replace(id: Int, fragment: FragmentJovenes, tag: String) {
}

private fun Any.replace(id: Int, fragment: FragmentMisOfertas, tag: String) {
}

