package com.dicoding.mainactivity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.dicoding.mainactivity.Adapter.MyPlantAdapter
import com.dicoding.mainactivity.Data.MyPlantData
import com.dicoding.mainactivity.Database.DatabaseContract
import com.dicoding.mainactivity.Database.MyPlantHelper
import com.dicoding.mainactivity.databinding.ActivityAddPlantBinding

class AddPlantActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddPlantBinding
    private lateinit var plantHelper: MyPlantHelper
    private var data: MyPlantData? = null
    private var selectedItem:String = ""

    private var note: MyPlantData? = null
    private var position: Int = 0

    companion object{
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //open database
        plantHelper = MyPlantHelper.getInstance(applicationContext)
        plantHelper.open()
        setSelectPlant()

        binding.btnSubmit.setOnClickListener(this)
    }

    private fun setSelectPlant(){
        val spinner = binding.selectPlant
        val items = arrayOf("Tomat", "Cabai", "Kentang", "Turnip", "Wortel", "Apel", "Jeruk")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            items
        )
        spinner.setAdapter(adapter)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                arg2: Int,
                arg3: Long
            ) {

                val items = spinner.selectedItem.toString()
                selectedItem = items
            }
            override fun onNothingSelected(arg0: AdapterView<*>?) {
                return
            }
        }
    }
    override fun onClick(view: View) {

        if (view.id == R.id.btn_submit) {
            val values = ContentValues()
            val plantName = selectedItem.toString()
            var Photo: Int? = null
            Photo = R.drawable.ic_pictures
            val harvestTime = binding.edtPlantHarvest.text.toString().trim()
            val plantTime = binding.edtPlantTime.text.toString() .trim()
            if (plantName.isEmpty()) {
                binding.edtPlantTime.error = "Field can not be blank"
                return
            }
            note?.plant_name = plantName
            note?.time_harvest = harvestTime
            note?.PHOTO = Photo.toString()

            note?.time_plant = plantTime
            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POSITION, position)

            values.put(DatabaseContract.PlantColumns.PLANT_NAME, plantName)
            values.put(DatabaseContract.PlantColumns.TIME_PLANT, harvestTime)
            values.put(DatabaseContract.PlantColumns.PHOTO, Photo.toString())
            values.put(DatabaseContract.PlantColumns.TIME_HARVEST, plantTime)
                val result = plantHelper.insert(values)
                if (result > 0) {
                    note?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }