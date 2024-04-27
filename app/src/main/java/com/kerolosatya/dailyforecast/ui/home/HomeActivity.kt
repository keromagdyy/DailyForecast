package com.kerolosatya.dailyforecast.ui.home

import android.os.Bundle
import com.kerolosatya.dailyforecast.databinding.ActivityHomeBinding
import com.kerolosatya.dailyforecast.ui.base.BaseActivity

class HomeActivity : BaseActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
    }

    private fun init() {

    }

    private fun onClick() {

    }
}