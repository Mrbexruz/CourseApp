package com.example.codialapp.db

import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar
import com.example.codialapp.models.Talabalar

interface DbInterface {
    fun addKurs(kurslar: Kurslar)
    fun showKurs():ArrayList<Kurslar>

    fun addMentor(mentorlar: Mentorlar)
    fun editMentor(mentorlar: Mentorlar)
    fun deleteMentor(mentorlar: Mentorlar)
    fun showMentor():ArrayList<Mentorlar>

    fun addGroup(grupalar: Grupalar)
    fun showGroup():ArrayList<Grupalar>
    fun editGroup(grupalar: Grupalar)
    fun deleteGroup(grupalar: Grupalar)

    fun addStudent(studentData: Talabalar)
    fun showStudents(): ArrayList<Talabalar>
    fun deleteStudent(talabalar: Talabalar)
    fun editStudent(talabalar: Talabalar)

    fun getCourseById(id: Int):Kurslar
    fun getMentorById(id: Int):Mentorlar
    fun getGroupById(id: Int):Grupalar
}