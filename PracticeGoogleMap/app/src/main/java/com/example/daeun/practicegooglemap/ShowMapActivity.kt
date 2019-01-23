package com.example.daeun.practicegooglemap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_show_map.*

class ShowMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var lat = 0.0
    var lng = 0.0
    var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_map)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.show_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lat = intent.getDoubleExtra("lat", 0.0)
        lng = intent.getDoubleExtra("lng", 0.0)
        address = intent.getStringExtra("address")

        text_show_map_no.setOnClickListener {
            finish()
        }

        text_show_map_yes.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("address", address)
            intent.putExtra("lat", lat)
            intent.putExtra("lng", lng)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 16f))
        mMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(address).snippet("위도: ${String.format("%.3f", lat)}, 경도: ${String.format("%.3f", lng)}"))
    }
}
