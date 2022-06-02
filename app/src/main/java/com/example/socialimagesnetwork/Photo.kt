package com.example.socialimagesnetwork

import android.icu.text.CaseMap
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Photo(var id:String = "",
                 var title:String = "",
                 var photoUrl: String = "",
                 var likeList: Map<String, Boolean> = mutableMapOf())
