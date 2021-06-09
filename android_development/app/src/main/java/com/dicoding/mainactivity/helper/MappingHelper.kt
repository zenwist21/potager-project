package com.dicoding.mainactivity.helper

import android.database.Cursor
import com.dicoding.mainactivity.Data.MyPlantData
import com.dicoding.mainactivity.Database.DatabaseContract
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<MyPlantData> {
        val fav = ArrayList<MyPlantData>()
        notesCursor?.apply {
            while (moveToNext()) {
                val ID = getInt(getColumnIndexOrThrow(DatabaseContract.PlantColumns._ID))
                val plantName = getString(getColumnIndexOrThrow(DatabaseContract.PlantColumns.PLANT_NAME))
                val timeHarvest = getString(getColumnIndexOrThrow(DatabaseContract.PlantColumns.TIME_HARVEST))
                val timePlant = getString(getColumnIndexOrThrow(DatabaseContract.PlantColumns.TIME_PLANT))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.PlantColumns.PHOTO))
                fav.add(
                        MyPlantData(
                                ID,
                                plantName,
                                timePlant,
                                timeHarvest,
                                photo
                        )
                )
            }
        }
        return fav
    }
}