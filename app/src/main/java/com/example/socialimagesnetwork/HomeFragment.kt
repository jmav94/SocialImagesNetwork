package com.example.socialimagesnetwork

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.socialimagesnetwork.databinding.FragmentAddBinding
import com.example.socialimagesnetwork.databinding.FragmentHomeBinding
import com.example.socialimagesnetwork.databinding.ItemSnapshotBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<Photo,PhotoHolder>
    private lateinit var mLayaoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseDatabase.getInstance().reference.child("photos")

        val options = FirebaseRecyclerOptions.Builder<Photo>()
            .setQuery(query, Photo::class.java).build()

        //Adaptador
        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Photo, PhotoHolder>(options){
            private lateinit var mContext: Context
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
                mContext = parent.context

                val view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_snapshot, parent, false)
                return PhotoHolder(view)
            }

            override fun onBindViewHolder(holder: PhotoHolder, position: Int, model: Photo) {
                val photo = getItem(position)

                with(holder){
                    setListener(photo)

                    binding.tvTittle.text = photo.title
                    Glide.with(mContext)
                        .load(photo.photoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.imgPhoto)
                }

            }

            @SuppressLint("NotifyDataSetChanged")// Error interno Firebase UI
            override fun onDataChanged() {
                super.onDataChanged()
                mBinding.progressBar.visibility = View.GONE
                notifyDataSetChanged()
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Toast.makeText(mContext, error.message, Toast.LENGTH_SHORT).show()
            }

        }

        mLayaoutManager = LinearLayoutManager(context)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLayaoutManager
            adapter = mFirebaseAdapter
        }


    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemSnapshotBinding.bind(view)
        fun setListener (photo: Photo){

        }
    }
}