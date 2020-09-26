package com.shanu.pokemoncatch

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
        loadPokemon()
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
        var myLocation = MylocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3
            ,3f,myLocation)
        // Will do it soon
        var mythread = myThread()
        mythread.start()
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

    }
    var location:Location?=null
    // Get use location
    inner class MylocationListener:LocationListener{
        constructor(){
            location = Location("Start")
            location!!.latitude = 0.0
            location!!.longitude = 0.0


        }
        override fun onLocationChanged(p0: Location) {
            location = p0
            //TODO("Not yet implemented")

        }
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String) {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    var oldLocation:Location?=null
    inner class myThread:Thread{
        constructor():super(){
            oldLocation = Location("Start")
            oldLocation!!.latitude = 0.0
            oldLocation!!.longitude = 0.0



        }

        override fun run(){
            while(true){
                try{

                    if(oldLocation!!.distanceTo(location)==0f){
                        continue
                    }
                    oldLocation = location
                    runOnUiThread {
                        mMap.clear()

                        // Show my player
                        val pikachu = LatLng(location!!.latitude, location!!.longitude)
                        mMap.addMarker(
                            MarkerOptions().position(pikachu)
                                .title("Me")
                                .snippet(" pika pika ")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pikachu))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pikachu, 10f))

                        // Show others

                        for(i in 0 until listPokemon.size){
                            var new = listPokemon[i]
                            if(new.isCatched == false){
                                val addedPokemon = LatLng(new.location!!.latitude, new.location!!.longitude)
                                mMap.addMarker(
                                    MarkerOptions().position(addedPokemon)
                                        .title(new.name!!)
                                        .snippet(new.des!!)
                                        .icon(BitmapDescriptorFactory.fromResource(new.image!!))
                                )

                            }


                        }
                    }
                    Thread.sleep(1000)



                }catch (ex:Exception){}
            }
        }
    }
    var listPokemon = ArrayList<Pokemon>()

    fun loadPokemon(){
        listPokemon.add(
            Pokemon("Charmander","A fire type pokemon"
        ,R.drawable.charmander,55.0,0.0,0.0)
        )
        listPokemon.add(Pokemon("Bulbasaur","A grass type pokemon"
            ,R.drawable.bulbasaur,67.0,1.0,1.0))
        listPokemon.add(Pokemon("Squirtle","A water type pokemon"
            ,R.drawable.squirtle,72.0,1.0,2.0))
    }
}
