package com.lyne.annexe1

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
}