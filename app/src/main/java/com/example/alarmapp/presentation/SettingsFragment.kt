package com.example.alarmapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alarmapp.R
import com.example.alarmapp.TimerViewModel
import com.example.alarmapp.data.model.TimerModel
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


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) {
            val model = viewModel.timer.value
            timers(model)
        }
    }

    private fun timers(model: TimerModel) {
        binding.npF.value = model.focusTime
        binding.npSb.value = model.shortBreak
        binding.npLb.value = model.longBreak
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.npF.minValue = 1
        binding.npF.maxValue = 59

        binding.npSb.minValue = 1
        binding.npSb.maxValue = 10

        binding.npLb.minValue = 10
        binding.npLb.maxValue = 30

        viewModel.getModel()
        timers(viewModel.timer.value)

        binding.saveButton.setOnClickListener {
            viewModel.saveModel(binding.npF.value * 60, binding.npSb.value * 60, binding.npLb.value * 60)
        }
    }
}