package com.unichamba1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unichamba1.Fragmentos.FragmentJovenes

class FiltrarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtrar)

        // Agregar el fragmento FragmentJovenes a esta actividad
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, FragmentJovenes())
            .commit()
    }
}