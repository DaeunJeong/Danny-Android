package com.example.daeun.practicegooglemap

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_search_location.*
import java.io.IOException
import java.util.*

class SearchLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)

        val geocoder = Geocoder(this, Locale.KOREA)
        var addressList: List<Address> = emptyList()

        val searchLocationItems = arrayListOf<SearchLocationItem>()

        val searchLocationAdapter = SearchLocationAdapter(this, searchLocationItems)
        recycler_search_location.adapter = searchLocationAdapter

        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_search_location.layoutManager = lm
        recycler_search_location.setHasFixedSize(true)

        recycler_search_location.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_search_location, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@SearchLocationActivity, ShowMapActivity::class.java)
                intent.putExtra("lat", addressList[position].latitude)
                intent.putExtra("lng", addressList[position].longitude)
                intent.putExtra("address", addressList[position].getAddressLine(position))
                startActivity(intent)
            }

            override fun onLongItemClick(view: View?, position: Int) {}
        }))

        btn_search_location.setOnClickListener {
            addressList = geocoder.getFromLocationName(edit_search_location.text.toString(), 10)

            for (i in 0 until addressList.size) {
                text_search_location_none.visibility = View.INVISIBLE
                searchLocationItems.clear()
                searchLocationItems.add(SearchLocationItem(addressList[i].getAddressLine(i)))
                searchLocationAdapter.notifyDataSetChanged()
            }

            if (addressList.isEmpty()) {
                searchLocationItems.clear()
                searchLocationAdapter.notifyDataSetChanged()
                text_search_location_none.visibility = View.VISIBLE
            }
        }
    }
}
