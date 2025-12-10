package com.surendramaran.yolov8tflite

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.surendramaran.yolov8tflite.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    companion object {
        const val PREFS_NAME = "YOLOv8_SETTINGS"
        const val CONFIDENCE_THRESHOLD_KEY = "CONFIDENCE_THRESHOLD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val currentThreshold = sharedPreferences.getFloat(CONFIDENCE_THRESHOLD_KEY, 0.1f)
        binding.sbThreshold.progress = (currentThreshold * 100).toInt()
        binding.tvThresholdValue.text = String.format("%.2f", currentThreshold)

        binding.sbThreshold.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val threshold = progress / 100f
                binding.tvThresholdValue.text = String.format("%.2f", threshold)
                with(sharedPreferences.edit()) {
                    putFloat(CONFIDENCE_THRESHOLD_KEY, threshold)
                    apply()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}