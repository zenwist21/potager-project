package com.dicoding.mainactivity.Identification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.mainactivity.databinding.ActivityCameraBinding
import com.dicoding.mainactivity.databinding.ActivityIdentificationBinding

class IdentificationActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_DATA = "DATA"
    }
    private lateinit var binding: ActivityIdentificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}