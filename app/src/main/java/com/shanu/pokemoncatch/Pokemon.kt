package com.shanu.pokemoncatch

import android.location.Location

class Pokemon {
    var name:String?=null
    var des:String?=null
    var image:Int?=null
    var power:Double?=null
    var location:Location?=null
    var isCatched:Boolean?=false


    constructor(name:String,des:String,image:Int,power:Double,latitude:Double,longitude:Double){
        this.name = name
        this.des = des
        this.image = image
        this.power = power
        this.location = Location(name)
        this.location!!.latitude = latitude
        this.location!!.longitude = longitude
        this.isCatched = false

    }
}