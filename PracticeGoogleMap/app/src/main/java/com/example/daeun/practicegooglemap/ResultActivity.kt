package com.example.daeun.practicegooglemap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        text_result_address.text = "주소: ${intent.getStringExtra("address")}"
        text_result_lat.text = "위도: ${intent.getDoubleExtra("lat",0.0)}"
        text_result_lng.text = "경도: ${intent.getDoubleExtra("lng",0.0)}"
    }
}
