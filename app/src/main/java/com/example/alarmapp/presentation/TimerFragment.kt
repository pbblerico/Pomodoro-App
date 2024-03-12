package com.example.alarmapp.presentation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.alarmapp.MyReceiver
import com.example.alarmapp.R
import com.example.alarmapp.TimerService
import com.example.alarmapp.TimerViewModel
import com.example.alarmapp.databinding.FragmentTimerBinding
import com.example.alarmapp.utils.Actions
import com.example.alarmapp.utils.Extras
import com.example.alarmapp.utils.TimerMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    private val viewModel: TimerViewModel by activityViewModels()

    private var active = false

    private val receiver = MyReceiver()
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            setTimer()

            Log.d("timer", "model : ${viewModel.timer.value.focusTime}")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipGroup.check(binding.f.id)
        setTimer()
        binding.button.setOnClickListener {


            if (!active) {
                val intent = Intent(requireContext(), TimerService::class.java)
                intent.action = Actions.START_TIMER.action
                intent.putExtra(Extras.SET_TIMER.key, updateCheckedChip(binding.chipGroup.checkedChipId))
                requireContext().startService(intent)
            } else {
//                binding.button.text = getString(R.string.start)
            }
            active = !active
        }
    }

    private fun getModeByChipId(id: Int): TimerMode {
        return when (id) {
            binding.f.id -> {
                TimerMode.FOCUS
            }
            binding.sb.id -> {
                TimerMode.SHORT_BREAK
            }
            else -> {
                TimerMode.LONG_BREAK
            }
        }
    }

    private fun updateCheckedChip(id: Int): Int {
        val mode = getModeByChipId(binding.chipGroup.checkedChipId)
        return viewModel.getTimerByMode(mode)
    }

    private fun setTimer() {
        viewModel.getModel()
        setTime(updateCheckedChip(binding.chipGroup.checkedChipId))
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                setTime(updateCheckedChip(checkedId))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(receiver, IntentFilter("com.example.alarmapp.TIMER_UI"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(receiver)
    }

    private fun setTime(time: Int) {
        binding.progressBar.max = time
        binding.progressBar.progress = binding.progressBar.max
        val seconds = time % 60 % 60
        val minutes = time / 60 % 60
        val hours = time / 3600
        binding.progress.text = if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}