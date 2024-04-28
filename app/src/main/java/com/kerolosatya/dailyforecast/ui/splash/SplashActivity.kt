package com.kerolosatya.dailyforecast.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.kerolosatya.dailyforecast.data.db.pref.SharedPreferenceHelper
import com.kerolosatya.dailyforecast.ui.home.HomeActivity
import com.kerolosatya.dailyforecast.databinding.ActivitySplashBinding
import com.kerolosatya.dailyforecast.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        enableEdgeToEdge()

        SharedPreferenceHelper.init(this@SplashActivity)

        binding.animationView.setAnimation("lottie_weather.json")
        lifecycleScope.launch {
            delay(3000)

            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

}