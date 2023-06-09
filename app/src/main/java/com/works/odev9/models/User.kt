package com.works.odev9.models

data class User(
    val UID: String = "",
    val email: String = "",
    val password: String = "",
    val visitedPlaces: List<Place> = listOf()
)

data class Place(
    var documentId : String = "",
    val title : String ="",
    val city : String = "",
    val date : String = "",
    val note : String? = ""
)
