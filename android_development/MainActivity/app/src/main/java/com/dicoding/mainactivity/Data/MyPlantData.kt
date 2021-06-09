package com.dicoding.mainactivity.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MyPlantData (
        var id: Int ,
        var plant_name: String = "",
        var time_plant: String = "",
        var time_harvest: String = "",
        var PHOTO: String = ""

        ):Parcelable