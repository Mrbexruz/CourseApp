package com.example.codialapp.models


class Mentorlar {
    var id:Int? = null
    var name:String? = null
    var lastName:String? = null
    var number:String? = null
    var kurs_id:Kurslar? = null

    constructor(id: Int?, name: String?, lastName: String?, number: String?, kurs_id: Kurslar?) {
        this.id = id
        this.name = name
        this.lastName = lastName
        this.number = number
        this.kurs_id = kurs_id
    }

    constructor(name: String?, lastName: String?, number: String?, kurs_id: Kurslar?) {
        this.name = name
        this.lastName = lastName
        this.number = number
        this.kurs_id = kurs_id
    }

    override fun toString(): String {
        return "Mentorlar(id=$id, name=$name, lastName=$lastName, number=$number, kurs_id=$kurs_id)"
    }


}