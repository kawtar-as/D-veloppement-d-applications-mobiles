package com.kawtar.tp1

import java.io.Serializable
// data class Serializable: contient tout ce qu'il faut pour reprendre la lecture
// index = chanson courante, tempsMs = endroit dans la chanson en ms
data class Sauvegarde(val index:Int ,val artists:ArrayList<String>, val titres: ArrayList<String>,
                      val audios: ArrayList<String>,
                      val pochettes: ArrayList<String>, val tempsMs: Long,): Serializable {}
