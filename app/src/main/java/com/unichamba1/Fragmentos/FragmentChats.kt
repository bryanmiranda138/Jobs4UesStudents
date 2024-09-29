package com.unichamba1.Fragmentos
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.unichamba1.OpcionesLogin
import com.unichamba1.R
import com.unichamba1.databinding.FragmentCuentaBinding
import android.widget.Toast
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.net.Uri


class FragmentChats : Fragment() {
    private lateinit var binding: FragmentCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContext: Context
    private val REQUEST_CODE_EDIT_PROFILE = 1



    override fun onAttach(context: Context) {
        mContext=context
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding=FragmentCuentaBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()

        binding.BtnCerrarSesion.setOnClickListener {
            startActivity(Intent(mContext,OpcionesLogin::class.java))
            activity?.finishAffinity()
        }


    }








}




