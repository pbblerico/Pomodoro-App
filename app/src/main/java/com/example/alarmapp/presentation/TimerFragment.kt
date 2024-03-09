package com.example.alarmapp.presentation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.alarmapp.CustomCountDownTimer
import com.example.alarmapp.R
import com.example.alarmapp.TimerService
import com.example.alarmapp.TimerViewModel
import com.example.alarmapp.databinding.FragmentTimerBinding
import com.example.alarmapp.utils.TimerMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    private val viewModel: TimerViewModel by activityViewModels()

    private var active = false
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


//        val timer = CustomCountDownTimer(10)
//        viewLifecycleOwner.lifecycleScope.launch {
//            timer.tick(this)
//            var running = false
//            binding.button.setOnClickListener {
//                if(running) {
//                    timer.stop()
//                    running = false
//                }
//                else {
//                    running = true
//                    start(timer)
//                }
//            }
//        }


//        binding.chipGroup.check(binding.f.id)
//        var timer = setTimer(viewModel.timer.value.timeLeft)
//        binding.button.setOnClickListener {
//            if(!active) {
//                timer.start()
//                binding.button.text = getString(R.string.stop)
//            } else {
//                timer.cancel()
//                binding.button.text = getString(R.string.start)
//            }
//            active = !active
//
//            val serviceIntent = Intent(requireActivity(), TimerService::class.java)
//            requireActivity().startService(serviceIntent)
//        }
//
//       binding.chipGroup.setOnCheckedChangeListener {group, checkedId ->
//            if(checkedId != -1) {
//                val mode = when(checkedId) {
//                    binding.f.id -> {
//                        TimerMode.FOCUS}
//                    binding.sb.id -> {
//                        TimerMode.SHORT_BREAK}
//                    else -> {
//                        TimerMode.LONG_BREAK}
//                }
////                timer = setTimer()
//            }
//        }
    }

    fun start(timer: CustomCountDownTimer) {
//        viewLifecycleOwner.lifecycleScope.launch {
//            timer.tick(this)
//        }
    }
    private fun setTime() {
        val time = when(viewModel.timer.value.curMode) {
            TimerMode.FOCUS -> viewModel.timer.value.focusTime
            TimerMode.SHORT_BREAK -> viewModel.timer.value.shortBreak
            TimerMode.LONG_BREAK -> viewModel.timer.value.longBreak
        }

        binding.progressBar.max = (time / 1000).toInt()
        binding.progressBar.progress = binding.progressBar.max

        setTime(time)
    }

    private fun setTime(time: Long) {
        val hours = time / 1000 / 3600
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60 % 60
        binding.progress.text = if(hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }

    private fun setTimer(time: Long): CountDownTimer {
        val mediaPlayer = MediaPlayer.create(context, R.raw.positive_notification)
        setTime()
        //todo replace with coroutine realization
        return object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                setTime(p0)
                binding.progressBar.progress = (p0 / 1000).toInt()
            }

            override fun onFinish() {
                mediaPlayer.start()
            }
        }
    }
}