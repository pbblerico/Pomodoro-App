package com.example.alarmapp.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.alarmapp.CustomCountDownTimer
import com.example.alarmapp.R
import com.example.alarmapp.TimerService
import com.example.alarmapp.databinding.ActivityMainBinding
import com.example.alarmapp.utils.Actions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val timerFragment = TimerFragment()
    private val settingsFragment = SettingsFragment()

    private var activeFragment: Fragment = timerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, TimerService::class.java)
        serviceIntent.setAction(Actions.START_TIMER.action)
        serviceIntent.putExtra(Actions.SET_TIMER.action, 17)
        startService(serviceIntent)

        //todo fix navigation
        supportFragmentManager.commit {
            add(binding.fragment.id, settingsFragment)
            hide(settingsFragment)
            add(binding.fragment.id, timerFragment)
        }

        binding.bottomNav.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId){
                R.id.TimerFragment -> {setFragment(timerFragment)}
                R.id.SettingsFragment -> {setFragment(settingsFragment)}
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.commit {
            hide(activeFragment)
            show(fragment)
            activeFragment = fragment
        }
        return true
    }
}