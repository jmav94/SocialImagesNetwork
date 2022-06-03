package com.example.socialimagesnetwork

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.provider.MediaStore
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
        // Intent
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_GALLERY) // Lanzar Galeria
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == RC_GALLERY){
                mPhotoSelectedUri = data?.data
                // Recibir Imagen
                mBinding.imgPhoto.setImageURI(mPhotoSelectedUri)
                // Mostrar en el Image View
                mBinding.tilTitle.visibility = View.VISIBLE
                mBinding.tvMessage.text = getString(R.string.post_message_valid_title)
            }
        }
    }






}