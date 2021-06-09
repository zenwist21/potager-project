package com.dicoding.mainactivity.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException
import kotlin.jvm.Throws

class MyPlantHelper (context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.PlantColumns.TABLE_NAME
            private var INSTANCE: MyPlantHelper? = null

        fun getInstance(context: Context): MyPlantHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: MyPlantHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun getQuery(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.PlantColumns._ID} ASC"
        )
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(
            DATABASE_TABLE,
            values,
            "${DatabaseContract.PlantColumns._ID} = ?",
            arrayOf(id)
        )
    }

    fun getQueryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.PlantColumns._ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun getQueryByUsername(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.PlantColumns.PLANT_NAME} = ?",
            arrayOf(id),
            null,
            null,
            "${DatabaseContract.PlantColumns.TIME_PLANT}",
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun delete(username: String): Int {
        return database.delete(
            DATABASE_TABLE,
            "${DatabaseContract.PlantColumns._ID} = '$username'",
            null
        )
    }
}
