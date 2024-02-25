package com.example.alarmapp

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
import com.example.alarmapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!allPermissionGranted()) {
                pushPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        val serviceIntent = Intent(this, TimerService::class.java)
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

    private val pushPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(
                this,
                if (it) "Permission granted" else "Permission NOT granted",
                Toast.LENGTH_SHORT
            ).show()
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.commit {
            hide(activeFragment)
            show(fragment)
            activeFragment = fragment
        }
        return true
    }
}