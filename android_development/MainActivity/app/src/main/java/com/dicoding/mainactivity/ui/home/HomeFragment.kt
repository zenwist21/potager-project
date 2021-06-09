package com.dicoding.mainactivity.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mainactivity.Adapter.MyPlantAdapter
import com.dicoding.mainactivity.AddPlantActivity
import com.dicoding.mainactivity.Data.MyPlantData
import com.dicoding.mainactivity.Database.MyPlantHelper
import com.dicoding.mainactivity.MainActivity
import com.dicoding.mainactivity.R
import com.dicoding.mainactivity.camera.CameraActivity
import com.dicoding.mainactivity.camera.CameraActivity2
import com.dicoding.mainactivity.databinding.FragmentHomeBinding
import com.dicoding.mainactivity.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var adapter:MyPlantAdapter
    private val  binding get() = bind_!!
    private var bind_ : FragmentHomeBinding? = null
    companion object{
        private const val EXTRA_VALUE = "value"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        bind_ = FragmentHomeBinding.bind(view)

        binding.btnNewplant.setOnClickListener{
            val intent = Intent(activity, AddPlantActivity::class.java)
            startActivity(intent)
        }
        binding.btnIdentifikasi.setOnClickListener{
            val intent = Intent(activity, CameraActivity2::class.java)
            startActivity(intent)
        }
        adapter = MyPlantAdapter()
        binding.apply {
            rvHome.setHasFixedSize(true)
            rvHome.layoutManager = LinearLayoutManager(activity)
            rvHome.adapter = adapter
        }
        adapter.notifyDataSetChanged()
        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<MyPlantData>(EXTRA_VALUE)
            if (list != null) {
                adapter.listPlant = list
            }
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_VALUE, adapter.listPlant)
    }
    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val plantHelper = MyPlantHelper.getInstance(requireContext())
            plantHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = plantHelper.getQuery()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listPlant = notes
            } else {
                adapter.listPlant = ArrayList()
            }
        }
    }



}