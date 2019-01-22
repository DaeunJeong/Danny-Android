package com.example.daeun.practicegooglemap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_start_choose_location.setOnClickListener {
            startActivity(Intent(this,ChooseLocationActivity::class.java))
        }

        btn_start_search_location.setOnClickListener {
            startActivity(Intent(this,SearchLocationActivity::class.java))
        }
    }
}
