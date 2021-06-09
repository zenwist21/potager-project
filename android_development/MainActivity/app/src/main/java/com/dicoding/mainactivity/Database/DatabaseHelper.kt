package com.dicoding.mainactivity.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.mainactivity.Database.DatabaseContract.PlantColumns.Companion.TABLE_NAME

internal class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbPotager"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_MYPLANT = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.PlantColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.PlantColumns.PLANT_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.PlantColumns.TIME_PLANT} TEXT NOT NULL," +
                " ${DatabaseContract.PlantColumns.TIME_HARVEST} TEXT NOT NULL," +
                " ${DatabaseContract.PlantColumns.PHOTO}  TEXT NULL)"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MYPLANT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.PlantColumns.TABLE_NAME}")
        onCreate(db)
    }
}