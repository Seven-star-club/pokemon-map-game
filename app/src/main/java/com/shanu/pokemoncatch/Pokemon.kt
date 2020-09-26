package com.shanu.pokemoncatch

class Pokemon {
    var name:String?=null
    var des:String?=null
    var image:Int?=null
    var power:Double?=null
    var latitude:Double?=null
    var longitude:Double?=null


    constructor(name:String,des:String,image:Int,power:Double,latitude:Double,longitude:Double){
        this.name = name
        this.des = des
        this.image = image
        this.power = power
        this.latitude = latitude
        this.longitude = longitude

    }
}