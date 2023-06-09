package com.works.odev9.ui.AddPlace

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.works.odev9.R
import com.works.odev9.databinding.FragmentAddPlaceBinding
import com.works.odev9.models.Place
import java.util.*


class AddPlaceFragment : Fragment() {

    private var _binding: FragmentAddPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        val root = binding.root

        var Date = ""
        val db = FirebaseFirestore.getInstance()
        val User = FirebaseAuth.getInstance().currentUser

        val txtTitle = binding.editTxtTitle
        val spinner = binding.spinnerCities
        val btnSetDate = binding.btnSetDate
        val btnSave = binding.btnSave
        val txtNote = binding.editTxtNote
        val txtSelectedDate = binding.txtSelectedDate

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Seçilen tarihi kullan

                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)

                // İşlemlerim
                var ay = "${selectedMonth + 1}"
                if (selectedMonth + 1 < 10) {
                    ay = "0${selectedMonth + 1}"
                }

                Date = "$selectedDayOfMonth.$ay.$selectedYear"
                txtSelectedDate.setText("Gezilecek tarih : $Date")



            },
            year,
            month,
            dayOfMonth
        )

        // Minimum tarih olarak bugünden önceki günleri belirle
        val minDate = Calendar.getInstance()
        minDate.add(Calendar.DAY_OF_MONTH, 0)
        datePickerDialog.datePicker.minDate = minDate.timeInMillis

        btnSetDate.setOnClickListener {
            datePickerDialog.show()
        }



        btnSave.setOnClickListener {

            val UID = User?.uid
            val title = txtTitle.text.toString()
            val city = spinner.selectedItem.toString()
            val date = Date
            val note = txtNote.text.toString() // Zorunlu bilgi değildir.
            if (title.isNotEmpty() && city.isNotEmpty() && date.isNotEmpty()) {
                var place = Place("",title, city, date, note)
                Log.d("Place",place.toString())

                db.collection("users").document(UID!!).collection("places").add(place)
                    .addOnSuccessListener {
                        Log.d("firestore", "Gezilecek yer eklendi ${it.id}")
                        place = Place(it.id,title, city, date, note)
                        Log.d("firestore",place.documentId)
                        findNavController().popBackStack()
                    }.addOnFailureListener {
                        Log.d("firestore", it.message.toString())
                    }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Lütfen zorunlu bilgileri eksiksiz doldurunuz",
                    Toast.LENGTH_LONG
                ).show()


            }


        }

        return root
    }

}