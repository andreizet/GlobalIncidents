package com.caphyon.globalincidents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import org.json.JSONObject
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.PolylineOptions





class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val thread = Thread(Runnable {
            try {
                val response =
                    URL("http://11.0.0.84:5002/get-incidents?limit=100")
                        .openStream()
                        .bufferedReader()
                        .use {
                            var x = 1
                            it.readText()
                        }

                val json = JSONArray(response)
                for (i in 0 until (json.length() - 1)) {
                    val item = json.getJSONObject(i)

                    this.runOnUiThread(Runnable {
                        val marker = LatLng(item.getDouble("lat"), item.getDouble("lng"))
                        mMap.addMarker(MarkerOptions().position(marker).title(item.getString("title")))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    marker.latitude,
                                    marker.longitude
                                ), 12.0f
                            )
                        )
                    })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()
    }
}
