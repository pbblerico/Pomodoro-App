package com.example.alarmapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alarmapp.databinding.FragmentTimerBinding

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaPlayer = MediaPlayer.create(context, R.raw.positive_notification)
        //todo replace with coroutine realization
        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {
                binding.progress.text = "${p0 / 1000}"
                binding.progressBar.progress = (p0 / 1000).toInt()

                Log.d("here", "${p0 / 10000 * 100}")
            }

            override fun onFinish() {
                mediaPlayer.start()
            }

        }
        binding.button.setOnClickListener {
            timer.start()
        }
    }
}