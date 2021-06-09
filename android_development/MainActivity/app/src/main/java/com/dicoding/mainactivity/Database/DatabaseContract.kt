package com.dicoding.mainactivity.Database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val content = "content"
    const val authority = "com.dicoding.submission_rev4"
    internal class PlantColumns : BaseColumns {
        companion object {

            const val TABLE_NAME = "myPlant"
            const val _ID = "id"
            const val PLANT_NAME = "plant_name"
            const val TIME_PLANT = "time_plant"
            const val TIME_HARVEST = "time_harvest"
            const val PHOTO = "photo"
            val CONTENT_URI: Uri = Uri.Builder().scheme(content)
                .authority(authority)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    }