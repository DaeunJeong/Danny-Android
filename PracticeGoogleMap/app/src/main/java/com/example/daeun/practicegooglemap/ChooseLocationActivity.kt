package com.example.daeun.practicegooglemap

import android.Manifest
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_choose_location.*
import android.location.*
import android.location.LocationManager
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.location.LocationListener
import android.content.Intent
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import java.util.*

class ChooseLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lat = 0.0
    private var lng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //GPS가 안켜져있을 경우 GPS를 켜라는 토스트메시지와 함께 GPS를 켤 수 있는 액티비티 띄워줌
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(applicationContext, "GPS를 켜세요", Toast.LENGTH_SHORT).show()
            val mMyTask = Runnable {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                //GPS 설정화면으로 이동
                startActivity(intent)
            }
            var mHandler = Handler()
            mHandler.postDelayed(mMyTask, 1500)
        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                lat = location.latitude
                lng = location.longitude
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat,lng),16f))
            }
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        //사용자의 현재 위치 구하기
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100f, locationListener)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val geocoder = Geocoder(this, Locale.KOREA)
        var list: List<Address> = emptyList()


        mMap.setOnCameraMoveStartedListener {
            text_location.text = "위치 이동 중..."
            text_choose_location_lat.text = "위도: 없음"
            text_choose_location_lng.text = "경도: 없음"
        }

        mMap.setOnCameraIdleListener {
            val locationCenter = mMap.cameraPosition.target
            list = geocoder.getFromLocation(locationCenter.latitude,locationCenter.longitude,1)
            if (list.isEmpty()) {
                text_location.text = "해당되는 주소 정보 없음"
            } else {
                text_location.text = list[0].getAddressLine(0)
                text_choose_location_lat.text = "위도: ${String.format("%.3f", locationCenter.latitude).toDouble()}"
                text_choose_location_lng.text = "경도: ${String.format("%.3f", locationCenter.longitude).toDouble()}"
            }
        }
    }
}
