package com.example.codialapp.models

class Grupalar {
    var id:Int? = null
    var name:String? = null
    var mentor_id:Mentorlar? = null
    var day:String? = null
    var time:String? = null
    var ochilganmi = 0

    constructor(
        id: Int?,
        name: String?,
        mentor_id: Mentorlar?,
        day: String?,
        time: String?,
        ochilganmi: Int
    ) {
        this.id = id
        this.name = name
        this.mentor_id = mentor_id
        this.day = day
        this.time = time
        this.ochilganmi = ochilganmi
    }

    constructor(
        name: String?,
        mentor_id: Mentorlar?,
        day: String?,
        time: String?,
        ochilganmi: Int
    ) {
        this.name = name
        this.mentor_id = mentor_id
        this.day = day
        this.time = time
        this.ochilganmi = ochilganmi
    }

    override fun toString(): String {
        return "Grupalar(id=$id, name=$name, mentor_id=$mentor_id, day=$day, time=$time, ochilganmi=$ochilganmi)"
    }

}