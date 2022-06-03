package com.example.socialimagesnetwork

import android.net.Uri
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialimagesnetwork.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private val RC_GALLERY = 18

    //Enlace
    private lateinit var mBinding: FragmentAddBinding

    private var mPhotoSelectedUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflar con Binding
        mBinding = FragmentAddBinding.inflate(inflater,container, false)
        return mBinding.root
    }

    // Configurar Botones
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnPost.setOnClickListener{ postPhoto() }

        mBinding.btnSelect.setOnClickListener{ openGallery() }
    }

    private fun postPhoto(){

    }

    private fun openGallery(){

    }
}