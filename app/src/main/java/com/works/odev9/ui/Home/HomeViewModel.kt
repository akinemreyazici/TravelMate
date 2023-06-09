package com.works.odev9.ui.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.works.odev9.models.Place

class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Place>>()
    val list: LiveData<List<Place>> get() = _list

    var thread: Thread? = null

    init {
        fetchData()
    }

    fun fetchData() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid
            db.collection("users").document(uid).collection("places")
                .get()
                .addOnSuccessListener { result ->
                    val placesList = ArrayList<Place>()
                    for (document in result) {
                        val place = document.toObject(Place::class.java)
                        place.documentId = document.id
                        placesList.add(place)
                    }
                    _list.postValue(placesList)
                }
                .addOnFailureListener { exception ->
                    Log.d("Firestore", "Error getting documents: ", exception)
                }
        }
    }



}