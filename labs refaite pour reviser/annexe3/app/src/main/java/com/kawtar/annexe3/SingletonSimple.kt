package com.kawtar.annexe3

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SingletonSimple {
    private var listeMemo = ArrayList<Memo>()



    // ajouter des memo dans la liste
    fun ajouterMemo(memoaAjoute :Memo):Unit{
        listeMemo.add(memoaAjoute)

    }

    fun voirMemoListe(): ArrayList<Memo>{
        return listeMemo
    }
    fun serialiserListe(context: Context){
        try{
            // permet de visualiser dans un telephone facilement
            val fos : FileOutputStream = context.openFileOutput("fichier.ser",MODE_PRIVATE)
            // buffer special pour ecrire dans le flux de donnees binaire
            val oos = ObjectOutputStream(fos)
            oos.use {
                 oos.writeObject(listeMemo)
            }
        }
        catch(e: Exception){
            println("on a un probleme")

        }
    }
    //récuperer la liste du fichier de séralisation et retoourner une copie de la liste
    fun desserialisationListe(context:Context){


            // pour eviter d'utiliser le fichier de seresialion sans en avoir vraiment besoin ('ajouter memo de suite
            if(listeMemo.isEmpty()) {
                val fis: FileInputStream = context.openFileInput("fichier.ser")
                val ois = ObjectInputStream(fis)
                ois.use {
                    listeMemo = ois.readObject() as ArrayList<Memo> // transtypage en kt est as
                }
            }
    }
}
