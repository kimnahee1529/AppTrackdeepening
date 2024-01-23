package com.nhkim.ex10_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nhkim.ex10_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            saveData()
            Toast.makeText(this, "Data saved.", Toast.LENGTH_SHORT).show()
        }

        loadData()
    }

    private fun saveData() {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("name", binding.etText.text.toString())
        edit.apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences("pref", 0)
        binding.etText.setText(pref.getString("name",""))
    }
}