package com.example.alarmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.example.alarmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //todo replace with coroutine realization
        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                binding.progress.text = "${p0 / 1000}"
                binding.progressBar.progress = (p0 / 1000).toInt()

                Log.d("here", "${p0 / 10000 * 100}")
            }

            override fun onFinish() {
            }

        }

        timer.start()
    }
}