package com.shanu.pokemoncatch

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()
    }
    var ACCESSLOCATION = 123
    fun checkPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat
                    .checkSelfPermission(this,android.Manifest.permission
                        .ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    ,ACCESSLOCATION)
                return
            }

        }
        getUserLocation()
    }

    fun getUserLocation(){

        Toast.makeText(this,"Location access is granted",Toast.LENGTH_SHORT).show()
        // Will do it soon
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            ACCESSLOCATION ->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getUserLocation()
                }else{
                    Toast.makeText(this
                        ,"Location access was not granted",Toast.LENGTH_SHORT).show()

                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val pikachu = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(pikachu)
            .title("Me")
            .snippet(" pika pika ")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pikachu)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pikachu,14f))
    }

    // Get use location
    inner class MyLocationListener:LocationListener{
        var location:Location?=null
        constructor(){
            location = Location("Start")
            location!!.latitude = 0.0
            location!!.longitude = 0.0


        }
        override fun onLocationChanged(location: Location) {
            this.location = location
            TODO("Not yet implemented")
        }

    }
}