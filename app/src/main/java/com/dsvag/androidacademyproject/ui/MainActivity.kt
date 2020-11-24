package com.dsvag.androidacademyproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dsvag.androidacademyproject.databinding.ActivityMainBinding
import com.dsvag.androidacademyproject.data.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}