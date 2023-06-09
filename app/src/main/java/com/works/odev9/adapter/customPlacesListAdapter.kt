package com.works.odev9.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.works.odev9.R
import com.works.odev9.models.Place

class customPlacesListAdapter(
    private val fragment: Fragment,
    private val list: List<Place>
) : ArrayAdapter<Place>(fragment.requireContext(), R.layout.places_list, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = fragment.layoutInflater.inflate(R.layout.places_list,null,true)

        val txtTitle = root.findViewById<TextView>(R.id.r_title)
        val txtCity = root.findViewById<TextView>(R.id.r_city)
        val txtDate = root.findViewById<TextView>(R.id.r_date)
        val txtNote = root.findViewById<TextView>(R.id.r_note)

        val place = list.get(position)
        txtTitle.text = place.title
        txtCity.text = "Şehir : " + place.city
        txtDate.text = "Tarih : " + place.date
        txtNote.text = "Not : " + place.note

        return root
    }
}
