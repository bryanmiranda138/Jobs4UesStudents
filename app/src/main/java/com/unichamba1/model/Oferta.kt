package com.unichamba1.model

data class Oferta(
    var id: String="",
    val description: String = "",
    val imagen: String = "",
    val quienPublica: String = "",
    val telefono: String = "",
    val imagenSmall: String = "",
    val carrera: List<String> = listOf() ,// Lista vacía por defecto
    val direction: String = ""
) {


}

