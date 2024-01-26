package com.nhkim.miseya

import DustItem
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.nhkim.miseya.databinding.ActivityMainBinding
import com.nhkim.miseya.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var items = mutableListOf<DustItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //도시 선택
        binding.spinnerViewSido.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            //요청 파라미터 생성
            communicateNetWork(setUpDustParameter(text))

        }

        binding.spinnerViewGoo.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->

            Log.d("miseya", "selectedItem: spinnerViewGoo selected > $text")
            var selectedItem = items.filter { f -> f.stationName == text }
            Log.d("miseya", "selectedItem: sidoName > " + selectedItem[0].sidoName)
            Log.d("miseya", "selectedItem: pm10Value > " + selectedItem[0].pm10Value)

            binding.tvCityname.text = selectedItem[0].sidoName + " " + selectedItem[0].stationName
            binding.tvDate.text = selectedItem[0].dataTime
            binding.tvP10value.text = selectedItem[0].pm10Value + "- ㎍/㎥"

            when (getGrade(selectedItem[0].pm10Value)) {
                1 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#9ED2EC"))
                    binding.ivFace.setImageResource(R.drawable.mise1)
                    binding.tvP10grade.text = "좋음"
                }

                2 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#D6A478"))
                    binding.ivFace.setImageResource(R.drawable.mise2)
                    binding.tvP10grade.text = "보통"
                }

                3 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#DF7766"))
                    binding.ivFace.setImageResource(R.drawable.mise3)
                    binding.tvP10grade.text = "나쁨"
                }

                4 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#BB3320"))
                    binding.ivFace.setImageResource(R.drawable.mise4)
                    binding.tvP10grade.text = "매우나쁨"
                }
            }

        }

    }

    //데이터 통신을 해야 하기 때문에 별도의 스레드 안에서 실행
    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch {
        val responseData = NetWorkClient.dustNetWork.getDust(param)

        items = responseData.response.dustBody.dustItems!!

        val goo = ArrayList<String>()
        items.forEach {
            Log.d("add Item: ", it.stationName)
            goo.add(it.stationName)
        }

        runOnUiThread {
            binding.spinnerViewGoo.setItems(goo)
        }
    }

    //요청 파라미터 생성
    private fun setUpDustParameter(sido: String): HashMap<String, String> {
        val authKey =
            "nrOGw0QURUK73GF9IZ1qwK2L/48kRarW7q/UHFbPSs1XJamzUDdYNTwLz81QgUlPLhJJDSUDzGGMPP1frVfl3w=="

        return hashMapOf(
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "ver" to "1.0"
        )
    }

    fun getGrade(value: String): Int {
        val mValue = value.toInt()
        var grade = 1
        grade = if (mValue in 0..30) {
            1
        } else if (mValue in 31..80) {
            2
        } else if (mValue in 81..100) {
            3
        } else 4
        return grade
    }


}