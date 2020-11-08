package com.dsvag.androidacademyproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dsvag.androidacademyproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}