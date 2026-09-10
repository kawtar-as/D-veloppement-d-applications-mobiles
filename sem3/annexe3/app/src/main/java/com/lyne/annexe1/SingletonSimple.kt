package com.kawtar.annexe1

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SingletonSimple {
    // liste de memos
    private var liste = ArrayList<Memo>()
    // fonction pour récuperer la liste

        fun getListe(): ArrayList<Memo>
        {
            return liste
        }

    // fonction pour ajouter un memo a la liste
    fun ajouterMemo(memoAjouter: Memo): Unit{
        liste.add(memoAjouter)
    }
    fun serialiserListe(context: Context){
        try {
            val fos: FileOutputStream = context.openFileOutput("fichier.ser",MODE_PRIVATE) // permet de visualiser dans un telephone facilement
            val oos =
                ObjectOutputStream(fos) // buffer special pour ecrire dans le flux de donnees binaire
            oos.use{
                oos.writeObject(liste)}
        }
        catch(e :Exception){
            println("problème")
        }


    }
    //récuperer la liste du fichier de séralisation et retoourner une copie de la liste
    fun desserialiserListe(context:Context){
        if(liste.isEmpty()) {
            val fis: FileInputStream =
                context.openFileInput("fichier.ser") // pour eviter d'utiliser le fichier de seresiali sans en avoir vraiment besoin  ('ajouter   memo de suite


            val ois =
                ObjectInputStream(fis)

            ois.use {
                liste = ois.readObject() as ArrayList<Memo> // transtype en kotlin est as
            }
        }

    }
}