package com.kawtar.tp1

interface Sujet {
    fun ajouterObservateur(o: Observateur)
    fun retirerObservateur(o: Observateur)
    fun avertirObservateurs()
}