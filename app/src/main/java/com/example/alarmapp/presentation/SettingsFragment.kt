package com.example.alarmapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alarmapp.R
import com.example.alarmapp.TimerViewModel
import com.example.alarmapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: TimerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.npF.minValue = 1
        binding.npF.maxValue = 59

        binding.npSb.minValue = 1
        binding.npSb.maxValue = 10

        binding.npLb.minValue = 10
        binding.npLb.maxValue = 30

        binding.saveButton.setOnClickListener {
            val focusTime = minutesToMilliSeconds(binding.npF.value)
            val shortBreakTime = minutesToMilliSeconds(binding.npSb.value)
            val longBreakTime = minutesToMilliSeconds(binding.npLb.value)
            Log.d("gere", "$focusTime, $shortBreakTime, $longBreakTime")
            viewModel.setTimers(focusTime, shortBreakTime, longBreakTime)
        }
    }

    private fun minutesToMilliSeconds(minutes: Int): Long {
        return minutes.toLong() * 60 * 1000
    }
}