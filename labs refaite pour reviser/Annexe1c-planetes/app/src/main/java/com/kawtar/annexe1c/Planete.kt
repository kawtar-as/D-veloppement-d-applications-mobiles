package com.kawtar.annexe1c

class Planete (var nom:String, var nombre: Int){

    override fun toString(): String {
        return "$nom($nombre sattelites)"
    }
}