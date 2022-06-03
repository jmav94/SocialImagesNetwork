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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddFragment : Fragment() {

    private val RC_GALLERY = 18
    private val PATH_PHOTO = "Photos"

    //Enlace
    private lateinit var mBinding: FragmentAddBinding
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference

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

        mStorageReference = FirebaseStorage.getInstance().reference
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(PATH_PHOTO)
    }

    private fun openGallery(){
        // Intent
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_GALLERY) // Lanzar Galeria
    }

    private fun postPhoto(){
        mBinding.progressBar.visibility = View.VISIBLE
        //mStorageReference.child(PATH_PHOTO).child("my_photo")
        val storageReference = mStorageReference.child(PATH_PHOTO).child("my_photo")
        if (mPhotoSelectedUri != null){
            storageReference.putFile(mPhotoSelectedUri!!)
                .addOnProgressListener {
                    val progress = (100 * it.bytesTransferred/it.totalByteCount).toDouble()
                    mBinding.progressBar.progress = progress.toInt()
                    mBinding.tvMessage.text = "$progress%"
                }
                .addOnCompleteListener{
                    mBinding.progressBar.visibility = View.INVISIBLE
                }
                .addOnSuccessListener {
                    Snackbar.make(mBinding.root, "Imagen publicada.",
                        Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Snackbar.make(mBinding.root, "No se logro subir la imagen, intente mas tarde.",
                        Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    private fun savePhoto(){

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