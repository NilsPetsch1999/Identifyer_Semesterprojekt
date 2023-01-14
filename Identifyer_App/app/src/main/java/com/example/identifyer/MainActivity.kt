package com.example.identifyer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.*
import com.example.identifyer.ViewModel.InmateViewModel
import com.example.identifyer.ViewModel.RoomViewModel
import com.example.identifyer.model.Room
import com.example.identifyer.ViewModel.UserViewModel
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.User
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    //Testdaten

    /*

                Inmates

                inmateViewModel.insert(Inmate("John","Doe", "male", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3", "3 years Karate", listOf("1", "2", "3"), 1 ))
                inmateViewModel.insert(Inmate("Maurice","Jefferson", "male", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3",
                 "3 years Karate", listOf("1", "2", "3"), 3 ))
                inmateViewModel.insert(Inmate("Patrick","Mahomes", "male", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3",
                 "3 years Karate", listOf("1", "2", "3"), 6 ))
                inmateViewModel.insert(Inmate("Nils","Petsch", "male", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3",
                 "3 years Karate", listOf("1", "2", "3"), 3 ))
                inmateViewModel.insert(Inmate("Flo","Huber", "male", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3",
                "3 years Karate", listOf("1", "2", "3"), 2 ))
                inmateViewModel.insert(Inmate("Christian","McCaffrey", "male", 3,"Austria","Drugs Dealing", 3, 3, 3,"Test3","Test3",
                 "3 years Karate", listOf("1", "2", "3"), 4 ))
                inmateViewModel.insert(Inmate("Felix","Engelmeier", "male3", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3",
                 "3 years Karate", listOf("1", "2", "3"), 6 ))
                inmateViewModel.insert(Inmate("Lara","Roth", "male3", 3,"Austria","Robbery", 3, 3, 3,"Test3","Test3",
                 "3 years Karate", listOf("1", "2", "3"), 5 ))

                Rooms
                roomViewModel.insert(Room("1.11", listOf<String>("1", "2", "3"), 2, 3, 2 ))
                roomViewModel.insert(Room("2.22", listOf<String>("1", "2", "3"), 4, 3, 2 ))
                roomViewModel.insert(Room("3.33", listOf<String>("1", "2", "3"), 3, 2, 2 ))
                roomViewModel.insert(Room("4.44", listOf<String>("1", "2", "3"), 2, 3, 3 ))
                roomViewModel.insert(Room("5.55", listOf<String>("1", "2", "3"), 1, 1, 4 ))
                roomViewModel.insert(Room("6.66", listOf<String>("1", "2", "3"), 5, 1, 4 ))

                User - kein Login authentifizierung
                userViewModel.insert(User("User1", "Password1"))
                userViewModel.insert(User("User2", "Password2"))
                userViewModel.insert(User("User3", "Password3"))
                userViewModel.insert(User("User4", "Password4"))
                userViewModel.insert(User("User5", "Password5"))




     */





    //Code scanner QR
    private lateinit var codeScanner: CodeScanner

    //View model inmate
    lateinit var inmateViewModel : InmateViewModel
    //ViewModel Rooms
    lateinit var roomViewModel : RoomViewModel
    //View Model user
    lateinit var userViewModel:UserViewModel

    var scannedRoom = Room()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        inmateViewModel = ViewModelProvider(this).get(InmateViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        Log.d("sdfadsf", "Done")
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                // to do: filter for room id works but needs to be changed !!!!!!!!!!!!!!!!!!!!!!!!! contains -> equals !!!!!!!!
                Log.d("ROOM", roomViewModel.getAllRooms().toString())
                Log.d("INMATE", inmateViewModel.getAllInmates().toString())
                Log.d("USER", userViewModel.getAllUsers().toString())
            }
        }



        if( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
        }else{
            startScanning()
        }

    }

    //Scanning function
    private fun startScanning(){
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera  = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled= true
        codeScanner.isFlashEnabled= false

        codeScanner.decodeCallback = DecodeCallback{

            val textString = it.text.toString()

            runOnUiThread {
                Toast.makeText(this, "Roomnumber :${it.text.toString()} ", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, RoomActivity::class.java)
            intent.putExtra(RoomActivity.ROOM_KEY, it.text.toString())
            startActivity(intent)


        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }


    //QR Code Scanner Permission for Camera
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123 ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permission granted" , Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //onResume lifecycle
    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }
    //onPause lifecycle
    override fun onPause() {
        if(::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()

    }


    // Menu create function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    //Menu when item is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.itemId
        var newUser2 :User
        if(itemId == R.id.action_settings){
            //What to do when clicking on settings
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }else if(itemId == R.id.action_loadDatabase){

            Log.d("Hello", inmateViewModel.getFilteredList("3").value.toString())



            lifecycleScope.launch {
                withContext(Dispatchers.IO){

                   var randomString  = roomViewModel.findRoomById("1".toLong())

                    //Log.d("Roomcheck", randomString.id.toString())
                    Log.d("Entries", inmateViewModel.getAllInmatesByRoomId(1).toString())
                    Log.d("Entries", inmateViewModel.getAllInmatesByRoomId(2).toString())
                    Log.d("Entries", randomString.toString())
                }

            }

            return true
        }
        return super.onOptionsItemSelected(item)
    }


}