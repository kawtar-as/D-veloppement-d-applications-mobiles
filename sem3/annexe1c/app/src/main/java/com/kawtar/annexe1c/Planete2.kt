package com.kawtar.annexe1c

data class Planete2(private var nom:String,private var nbSatellites:Int){
    // si on veut utilser un bloc init
    private var nbHabitants: Long = 0
    init{
        if(nom == "Terre")
            nbHabitants = 9_000_000_000

    }
    //constructeuur secondaire : doit se baser sur le primaire
    constructor(nom:String,nbSatellites: Int,nbHabitants:Long):this(nom,nbSatellites){
        this.
    }
}
