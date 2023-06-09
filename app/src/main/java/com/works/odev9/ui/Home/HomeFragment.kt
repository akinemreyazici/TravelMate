package com.works.odev9.ui.Home

import android.app.ActionBar
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.works.odev9.adapter.customPlacesListAdapter
import com.works.odev9.databinding.FragmentHomeBinding
import com.works.odev9.models.Place

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val actionBar: androidx.appcompat.app.ActionBar? = (activity as AppCompatActivity)?.supportActionBar
        if (actionBar != null) {
            val title = SpannableString(actionBar.title) // your title
            title.setSpan(
                ForegroundColorSpan(Color.BLACK), 0, title.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            actionBar.title = title
        }

        val root: View = binding.root

        val list = binding.listPlaces

        homeViewModel.list.observe(viewLifecycleOwner){
            val adapter = customPlacesListAdapter(this,it)
            list.adapter = adapter
        }

        list.setOnItemLongClickListener { adapterView, view, i, l ->
            val selectedItem = list.getItemAtPosition(i) as Place


            AlertDialog.Builder(requireContext())
                .setTitle("Gezilecek Yer Sil")
                .setMessage("Bu gezilecek yeri silmek istediğinizden emin misiniz?")
                .setPositiveButton("Evet") { dialog, which ->
                    DeleteItem(selectedItem.documentId!!)
                        }
                .setNegativeButton("Hayır", null) // "Hayır" seçeneği herhangi bir işlem yapmaz.
                .show()
            true
        }
        return root
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun DeleteItem(documentId : String) {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        db.collection("users").document(user!!.uid).collection("places").document(documentId).delete()
            .addOnSuccessListener {
            homeViewModel.fetchData() // Sildikten sonra listeyi güncellemek
        }.addOnFailureListener {
            Log.e("Firestore",it.message.toString())
        }
    }
}