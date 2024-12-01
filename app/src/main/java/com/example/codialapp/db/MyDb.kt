package com.example.codialapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar
import com.example.codialapp.models.Talabalar

class MyDb(context: Context) : SQLiteOpenHelper(context, "db_name", null, 1), DbInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table kurslar_table(id integer not null primary key autoincrement unique, name text not null, about text not null)")
        db?.execSQL("create table mentorlar_table(id integer not null primary key autoincrement unique, name text not null, lastName text not null,number text not null ,kurs_id integer not null, foreign key(kurs_id) references kurslar_table(id))")
        db?.execSQL("create table grupalar_table(id integer not null primary key autoincrement unique, name text not null,mentor_id integer not null,day text not null,time text not null,ochilganmi integer not null ,foreign key(mentor_id) references mentorlar_table(id))")
        db?.execSQL("create table talabalar_table(id integer not null primary key autoincrement unique, name text not null, lastName text not null, number text not null, day text not null, group_id integer not null, foreign key (group_id) references grupalar_table (id))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addKurs(kurslar: Kurslar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", kurslar.name)
        contentValues.put("about", kurslar.about)
        database.insert("kurslar_table", null, contentValues)
        database.close()
    }

    override fun showKurs(): ArrayList<Kurslar> {
        val list = ArrayList<Kurslar>()
        val database = this.readableDatabase
        val cursor = database.rawQuery("select * from kurslar_table", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Kurslar(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addMentor(mentorlar: Mentorlar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", mentorlar.name)
        contentValues.put("lastName", mentorlar.lastName)
        contentValues.put("number", mentorlar.number)
        contentValues.put("kurs_id", mentorlar.kurs_id?.id)
        database.insert("mentorlar_table", null, contentValues)
        database.close()
    }
    override fun editMentor(mentorlar: Mentorlar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", mentorlar.name)
        contentValues.put("lastName", mentorlar.lastName)
        contentValues.put("number", mentorlar.number)
        contentValues.put("kurs_id", mentorlar.kurs_id?.id)
        database.update("mentorlar_table", contentValues, "id = ?", arrayOf(mentorlar.id.toString()))
    }
    override fun deleteMentor(mentorlar: Mentorlar) {
        val database = this.writableDatabase
        database.delete("mentorlar_table", "id = ?", arrayOf(mentorlar.id.toString()))
    }

    override fun showMentor(): ArrayList<Mentorlar> {
        val list = ArrayList<Mentorlar>()
        val database = this.readableDatabase

        val cursor = database.rawQuery("select * from mentorlar_table", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Mentorlar(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        getCourseById(cursor.getInt(4))
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addGroup(grupalar: Grupalar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name",grupalar.name)
        contentValues.put("mentor_id",grupalar.mentor_id?.id)
        contentValues.put("day",grupalar.day)
        contentValues.put("time",grupalar.time)
        contentValues.put("ochilganmi",grupalar.ochilganmi)
        database.insert("grupalar_table",null,contentValues)
    }
    override fun editGroup(grupalar: Grupalar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", grupalar.name)
        contentValues.put("mentor_id", grupalar.mentor_id!!.id)
        contentValues.put("day", grupalar.day)
        contentValues.put("time", grupalar.time)
        contentValues.put("ochilganmi", grupalar.ochilganmi)
        database.update("grupalar_table", contentValues, "id = ?", arrayOf(grupalar.id.toString()))
    }
    override fun deleteGroup(grupalar: Grupalar) {
        val database = this.writableDatabase
        database.delete("grupalar_table", "id = ?", arrayOf(grupalar.id.toString()))
    }

    override fun showGroup(): ArrayList<Grupalar> {
        val list = ArrayList<Grupalar>()
        val database = this.readableDatabase

        val cursor = database.rawQuery("select * from grupalar_table", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Grupalar(
                        cursor.getInt(0),
                        cursor.getString(1).toString(),
                        getMentorById(cursor.getInt(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addStudent(talabalar: Talabalar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name",talabalar.firstName)
        contentValues.put("lastName",talabalar.lastName)
        contentValues.put("number",talabalar.number)
        contentValues.put("day",talabalar.day)
        contentValues.put("group_id",talabalar.groupId?.id)
        database.insert("talabalar_table",null,contentValues)
        database.close()
    }
    override fun deleteStudent(talabalar: Talabalar) {
        val database = this.writableDatabase
        database.delete("talabalar_table", "id = ?", arrayOf(talabalar.id.toString()))
    }
    override fun editStudent(talabalar: Talabalar) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", talabalar.firstName)
        contentValues.put("lastName", talabalar.lastName)
        contentValues.put("number", talabalar.number)
        contentValues.put("day", talabalar.day)
        contentValues.put("group_id", talabalar.groupId!!.id)
        database.update("talabalar_table", contentValues, "id = ?", arrayOf(talabalar.id.toString()))
    }

    override fun showStudents(): ArrayList<Talabalar> {
        val list = ArrayList<Talabalar>()
        val database = this.readableDatabase
        val query = "select * from talabalar_table"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Talabalar(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        getGroupById(cursor.getInt(5))
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getCourseById(id: Int): Kurslar {
        val database = this.readableDatabase
        val cursor = database.query(
            "kurslar_table",
            arrayOf("id", "name", "about"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val courseData = Kurslar(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
        return courseData
    }

    override fun getMentorById(id: Int): Mentorlar {
        val database = this.readableDatabase
        val cursor = database.query(
            "mentorlar_table",
            arrayOf("id", "name", "lastName","number","kurs_id"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val courseData = Mentorlar(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            getCourseById(cursor.getInt(4))
        )
        return courseData
    }
    override fun getGroupById(id: Int): Grupalar {
        val database = this.readableDatabase
        val cursor = database.query(
            "grupalar_table",
            arrayOf("id", "name", "mentor_id", "day", "time", "ochilganmi"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val groupData = Grupalar(
            cursor.getInt(0),
            cursor.getString(1),
            getMentorById(cursor.getInt(2)),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5)
        )
        return groupData
    }

}