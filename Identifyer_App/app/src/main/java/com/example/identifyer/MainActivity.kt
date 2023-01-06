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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //Code scanner QR
    private lateinit var codeScanner: CodeScanner


    lateinit var inmateViewModel : InmateViewModel
    //ViewModel Rooms
    lateinit var roomViewModel : RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        inmateViewModel = ViewModelProvider(this).get(InmateViewModel::class.java)



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

            Log.d("scan Qr Result", roomViewModel.findRoomById(it.text.toLong()).toString())
            var scannedRoom = roomViewModel.findRoomById(it.text.toLong())
            runOnUiThread {
                Toast.makeText(this, "Roomnumber :${scannedRoom.roomName} InmateList: ${scannedRoom.inmateList}", Toast.LENGTH_SHORT).show()
            }
            //intent to inmate list
            val intent = Intent(this, InmateListActivity::class.java)
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
            var newRoom = Room("1.11", listOf<String>("1", "2", "3"), 1)
            var newUser = User("User3333333333333","password3")
            var newInmate = Inmate("Firstname3","Lastname3", "male3", 3, 3, 3, 3,"Test3","Test3",
                listOf("1", "2"), listOf("1", "2", "3"), "3 years Karate", listOf("1", "2", "3"), 2 )
            //userViewModel.insert(newUser)
                //roomViewModel.insert(newRoom)
            //var randomString  = userViewModel.findUserById(1)
            inmateViewModel.insert(newInmate)



            lifecycleScope.launch {
                withContext(Dispatchers.IO){

                   var randomString  =roomViewModel.findRoomById(1)

                    //Log.d("Roomcheck", randomString.id.toString())
                    Log.d("Entries", inmateViewModel.getAllInmatesByRoomId(1).toString())
                    Log.d("Entries", inmateViewModel.getAllInmatesByRoomId(2).toString())
                    Log.d("Entries", inmateViewModel.getAllInmates().toString())
                }

            }

            return true
        }
        return super.onOptionsItemSelected(item)
    }


}