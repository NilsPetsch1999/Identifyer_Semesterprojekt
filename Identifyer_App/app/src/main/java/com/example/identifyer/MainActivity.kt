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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject

class MainActivity : AppCompatActivity() {



    //Code scanner QR
    private lateinit var codeScanner: CodeScanner

    //View model inmate
    lateinit var inmateViewModel : InmateViewModel
    //ViewModel Rooms
    lateinit var roomViewModel : RoomViewModel
    //View Model user
    lateinit var userViewModel:UserViewModel

    //Testdata
    var jsonTestData : String = "{\n" +
            "  \"room\": [\n" +
            "    {\"id\":\"1\", \"roomNumber\":\"1.11\", \"tract\" : \"A\", \"securityLevel\":\"1\", \"maxCapacity\": \"4\", \"inmateList\":[\"1\",\"3\", \"6\"]}, \n" +
            "  {\"id\":\"2\", \"roomNumber\":\"2.22\", \"tract\" : \"B\", \"securityLevel\":\"2\", \"maxCapacity\": \"4\", \"inmateList\":[\"1\",\"3\", \"6\"]}, \n" +
            "  {\"id\":\"3\", \"roomNumber\":\"3.33\", \"tract\" : \"C\", \"securityLevel\":\"3\", \"maxCapacity\": \"4\", \"inmateList\":[\"1\",\"3\", \"6\"]}, \n" +
            "  {\"id\":\"4\", \"roomNumber\":\"4.44\", \"tract\" : \"D\", \"securityLevel\":\"4\", \"maxCapacity\": \"4\", \"inmateList\":[\"1\",\"3\", \"6\"]}, \n" +
            "  {\"id\":\"5\", \"roomNumber\":\"5.55\", \"tract\" : \"E\", \"securityLevel\":\"5\", \"maxCapacity\": \"4\", \"inmateList\":[\"1\",\"3\", \"6\"]}, \n" +
            "  {\"id\":\"6\", \"roomNumber\":\"6.66\", \"tract\" : \"F\", \"securityLevel\":\"6\", \"maxCapacity\": \"4\", \"inmateList\":[\"1\",\"3\", \"6\"]}], \n" +
            "  \"inmates\": [\n" +
            "    {\"id\": \"1\", \"firstname\" : \"John\", \"lastname\": \"Doe\", \"gender\": \"male\", \"dateOfBirth\": \"11.11.1911\", \"nationality\":\"Austria\", \"sentence\": \"Robbery\", \"arrivalDate\": \"23.4.2020\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"1\", \"physicalWellness\":\"No Comment\", \"mentalWellness\":\"Depressions\", \"combatExperience\":\"7 years of UFC, Gangmember\", \"additionalNotes\":[\"Former Gangmember\", \"Likes to be funny\", \"sarcastic\"], \"room_id\": \"1\"}, {\"id\": \"2\", \"firstname\" : \"Nils\", \"lastname\": \"Petsch\", \"gender\": \"male\", \"dateOfBirth\": \"24.05.1999\", \"nationality\":\"Austria\", \"sentence\": \"Robbery\", \"arrivalDate\": \"25.05.2021\", \"TimeOfSentence\":\"5 Years\",\"securityLevel\":\"4\", \"physicalWellness\":\"knee porblems\", \"mentalWellness\":\"No comment\", \"combatExperience\":\"7 Karate, 3 Years Muy Thai\", \"additionalNotes\":[\"Former Footballer\", \"Likes to sniff gas\", \"loves alcohol\"], \"room_id\": \"2\"}, {\"id\": \"3\", \"firstname\" : \"Flo\", \"lastname\": \"Huber\", \"gender\": \"male\", \"dateOfBirth\": \"24.07.1988\", \"nationality\":\"Austria\", \"sentence\": \"Played Yi in League of Legends\", \"arrivalDate\": \"15.03.2021\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"3\", \"physicalWellness\":\"No Comment\", \"mentalWellness\":\"Who the fuck plays YI ???\", \"combatExperience\":\"One punch and u gone\", \"additionalNotes\":[\"Former E-Sport Player\", \"Likes small things\", \"loves YI\"], \"room_id\": \"3\"}, {\"id\": \"4\", \"firstname\" : \"Felix\", \"lastname\": \"Engelmeier\", \"gender\": \"male\", \"dateOfBirth\": \"14.12.1999\", \"nationality\":\"Austria\", \"sentence\": \"Did the homework\", \"arrivalDate\": \"10.06.2022\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"2\", \"physicalWellness\":\"No Comment\", \"mentalWellness\":\"No comment\", \"combatExperience\":\"10 Years Muy Thai\", \"additionalNotes\":[\"Former Pro Chess Player\", \"Likes to sniff on glue\", \"loves alcohol\"], \"room_id\": \"4\"}, {\"id\": \"5\", \"firstname\" : \"Lara\", \"lastname\": \"Roth\", \"gender\": \"female\", \"dateOfBirth\": \"13.11.1998\", \"nationality\":\"Austria\", \"sentence\": \"Supporting Planktion\", \"arrivalDate\": \"25.10.2021\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"4\", \"physicalWellness\":\"trained \", \"mentalWellness\":\"No comment\", \"combatExperience\":\"7 Karate, 3 Years Muy Thai\", \"additionalNotes\":[\"Former Athlete\", \"Likes to sry\", \"loves alcohol\"], \"room_id\": \"5\"}, {\"id\": \"6\", \"firstname\" : \"Patrick\", \"lastname\": \"Star\", \"gender\": \"male\", \"dateOfBirth\": \"13.12.1998\", \"nationality\":\"Austria\", \"sentence\": \"Eating a burger at 3 am\", \"arrivalDate\": \"25.10.2021\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"4\", \"physicalWellness\":\"star fish \", \"mentalWellness\":\"dumb\", \"combatExperience\":\"weights a lot\", \"additionalNotes\":[\"Former Sum-Ringer\", \"Likes to eat\", \"loves spongebob\"], \"room_id\": \"6\"}, {\"id\": \"7\", \"firstname\" : \"Spongebob\", \"lastname\": \"Schwammkopf\", \"gender\": \"male\", \"dateOfBirth\": \"23.12.1998\", \"nationality\":\"Austria\", \"sentence\": \"Forgetting how to do a crabby burger\", \"arrivalDate\": \"25.10.2021\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"5\", \"physicalWellness\":\"sponge\", \"mentalWellness\":\"smart sponge\", \"combatExperience\":\"spacuala master\", \"additionalNotes\":[\"Former Cook\", \"Likes to cook\", \"loves patrick\"], \"room_id\": \"1\"},{\"id\": \"8\", \"firstname\" : \"Tadeus\", \"lastname\": \"Tentakel\", \"gender\": \"male\", \"dateOfBirth\": \"23.12.1998\", \"nationality\":\"Austria\", \"sentence\": \"Forgetting how to do a crabby burger\", \"arrivalDate\": \"25.10.2021\", \"timeOfSentence\":\"5 Years\",\"securityLevel\":\"5\", \"physicalWellness\":\"sponge\", \"mentalWellness\":\"smart sponge\", \"combatExperience\":\"spacuala master\", \"additionalNotes\":[\"Former Cook\", \"Likes to cook\", \"loves patrick\"], \"room_id\": \"1\"}\n" +
            "  ]\n" +
            "}";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        inmateViewModel = ViewModelProvider(this).get(InmateViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        if( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
        }else{
            startScanning()
        }

    }

    //deleting rooms and inmates
     suspend private fun deleteAllRoomsInmates(){

        roomViewModel.deleteAllRooms();
        inmateViewModel.deleteAllInmates();
    }
        //response from http request --> parses Response and inserts the data
    private fun parseJson(reponse:String){

        var obj = JSONObject(reponse);
        var roomObj = obj["room"].toString();
        var inmateObj = obj["inmates"].toString();


        val typeTokenRooms = object : TypeToken<List<Room>>(){}.type;
        val rooms = Gson().fromJson<List<Room>>(roomObj, typeTokenRooms);

        val typeTokenInmates = object : TypeToken<List<Inmate>>(){}.type;
        val inmates = Gson().fromJson<List<Inmate>>(inmateObj, typeTokenInmates);

        for (item: Room in rooms ){
            val newRoom : Room = Room(item.id,item.roomNumber, item.inmateList, item.securityLevel, item.tract, item.maxCapacity );
            roomViewModel.insert(newRoom)
        }

        for (item: Inmate in inmates){
            val newInmate : Inmate = Inmate(item.id, item.firstname, item.lastname, item.gender, item.dateOfBirth, item.nationality, item.sentence, item.arrivalDate, item.timeOfSentence,item.securityLevel, item.physicalWellness, item.mentalWellness, item.combatExperience, item.additionalNotes, item.room_id)
            inmateViewModel.insert(newInmate)
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

        if(itemId == R.id.action_settings){
            //What to do when clicking on settings
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }else if(itemId == R.id.action_loadDatabase){
            //sync Database
            syncDatabase()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    //method which calls the http request method --> gets response as String which-->validate if empty --> delete all entries if not empty--> parses response and inserts the data
    private fun syncDatabase(){
        lifecycleScope.launch {
            deleteAllRoomsInmates()
            delay(1000)
            parseJson(jsonTestData)
        }
    }

}