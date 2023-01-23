package com.example.identifyer


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
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
import com.example.identifyer.ViewModel.UserViewModel
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParserException
import java.io.BufferedReader
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

    //Main
class MainActivity : AppCompatActivity() {



    //Code scanner QR
    private lateinit var codeScanner: CodeScanner

    //View model inmate
    lateinit var inmateViewModel : InmateViewModel
    //ViewModel Rooms
    lateinit var roomViewModel : RoomViewModel
    //View Model user
    lateinit var userViewModel:UserViewModel

    //ngrok URL which redirect to the port where the server runs --> Flo: 8080, Nils: 8081
    //url needs to be updated when new instance of--> ngrok http 8080 (port of the backend)
    //ngrok http 8080 --> creates and url which points at the pc port | example: https://fbaa-82-218-247-87.eu.ngrok.io -> points at -> http://localhost:8080/ (when backend runs on port: 8080)
    private val url :String = "https://fbaa-82-218-247-87.eu.ngrok.io"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        inmateViewModel = ViewModelProvider(this).get(InmateViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
        }else{
            startScanning()
        }

    }


    //deletes all rooms and inmates
    private suspend fun deleteAllRoomsInmates(){
        roomViewModel.deleteAllRooms()
        inmateViewModel.deleteAllInmates()
    }


    private fun parseJson(response:String){

        val obj = JSONObject(response)

        val roomsObj = obj["room"].toString()
        val inmatesObj  =obj["inmates"].toString()

        val typeTokenRooms = object : TypeToken<List<ResponseRoom>>(){}.type
        val rooms = Gson().fromJson<List<ResponseRoom>>(roomsObj, typeTokenRooms)

        val typeTokenInmates = object : TypeToken<List<ResponseInmate>>(){}.type
        val inmates = Gson().fromJson<List<ResponseInmate>>(inmatesObj, typeTokenInmates)

        for (item: ResponseRoom in rooms){
            val newRoom : Room = Room(item.id,item.roomNumber, item.securityLevel, item.tractName, item.maxInmateCount )
            roomViewModel.insert(newRoom)
        }

        for (item: ResponseInmate in inmates){
            val offenseList = ArrayList<String>();
            for(entry: Offense in item.offenseList){
                val s = "# "+ entry.id + " - " + entry.description + "\n" + convertLongToTime(entry.timeOfOffense)
                offenseList.add(s)
            }

            var nationality = item.nationality.joinToString(", ")

            var securityLevel = when(item.securityLevel){
                "LEVEL_HIGH"-> "High"
                "LEVEL_MEDIUM"-> "Medium"
                "LEVEL_LOW"-> "Low"
                else-> {
                    "NONE"
                }
            }

            var gender = when(item.gender){
                "GENDER_MALE"-> "male"
                "GENDER_FEMALE"-> "female"
                "GENDER_OTHER"-> "other"
                else-> {
                    "NONE"
                }
            }
            val newInmate : Inmate = Inmate(item.id, item.firstName, item.lastName, gender, convertLongToTimeWithoutTime(item.dateOfBirth), nationality, (item.sentence/31536000).toString() + " years", convertLongToTimeWithoutTime(item.arrivalDate), (TimeUnit.DAYS.convert(item.sentence, TimeUnit.NANOSECONDS)/30).toString(),securityLevel, item.physicalWellness, item.mentalWellness,offenseList, item.combatExperience.joinToString("\n#"), item.notes, item.room.id)
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
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled= false

        codeScanner.decodeCallback = DecodeCallback{

            val textString = it.text.toString()

            runOnUiThread {
                Toast.makeText(this, "RoomName :${it.text.toString()} ", Toast.LENGTH_SHORT).show()
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
        }else if(itemId == R.id.action_help){
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    //method which calls the http request method --> gets response as String which-->validate if empty --> delete all entries if not empty--> parses response and inserts the data
    private fun syncDatabase(){
           var response = requestData(url)
        lifecycleScope.launch {
            if(!response.isNullOrBlank()){
                deleteAllRoomsInmates()
                delay(1000)
                try {
                    parseJson(response)
                    delay(1000)
                    Toast.makeText(applicationContext,"Database updated successfully!", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(applicationContext,"Database update failed!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext,"No Data found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //http get call to a an address which redirects to the server localhost 8081
    // (command in ngronk) : ngronk http 8081 --> creates url which is visible for everyone!  points at localhost:8081 from the server (command giving device)
    private fun requestData(url:String) :String{

        var data: String =""
        var con: HttpURLConnection? = null
        try {

            val url = URL(url)
            con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            Log.d("response", con.inputStream.toString())
            val reader = BufferedReader(con.inputStream.reader())

            try {
                data = reader.readText()
            } finally {
                reader.close()
            }
        } catch (ex: MalformedURLException) {
            Log.e("LOG_TAG", ex.toString())


        } catch (ex: XmlPullParserException) {
            Log.e("LOG_TAG", ex.toString())


        } catch (ex: IOException) {
            Log.e("LOG_TAG", ex.toString())

        } finally {
            con?.disconnect()
        }

        return data
    }

    //converts Long to Date Format
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
    //converts Long to Date Format with out day time
    private fun convertLongToTimeWithoutTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }

}