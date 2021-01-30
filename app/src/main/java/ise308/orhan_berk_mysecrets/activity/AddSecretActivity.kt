package ise308.orhan_berk_mysecrets.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.util.DatabaseHelper
import java.util.*

class AddSecretActivity : AppCompatActivity() {

    // Object and Variable Definition
    lateinit var databaseHelper : DatabaseHelper
    lateinit var addTitle : EditText
    lateinit var addDescription : EditText
    lateinit var addButton : Button
    lateinit var highLight : CheckBox
    lateinit var addLocation : EditText

    var txtTitle = ""
    var txtDesc = ""
    var txtLocation = ""
    var hLight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        // Setting Action Bar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.mAdd))

        // Creating DatabaseHelper Object
        databaseHelper = DatabaseHelper(this)

        addTitle = findViewById(R.id.addTitle)
        addDescription = findViewById(R.id.addDescription)
        addButton = findViewById(R.id.addBTN)
        highLight = findViewById(R.id.checkBox)
        addLocation = findViewById(R.id.addLocation)


        addButton.setOnClickListener {

            addSecret()
            finish()

        }


    }

    // Toolbar Back Button Function
    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }


    fun addSecret(){

        // Converting EditText Values Into String
        txtTitle = addTitle.text.toString()
        txtDesc = addDescription.text.toString()
        txtLocation = addLocation.text.toString()
        // Check if user click HighLight Button
        if (highLight.isChecked){
            hLight = 1
        }

        var dTime = System.currentTimeMillis()
    //    Log.i("@@@ : ", "$dTime , $txtLocation")
/*
        val calender = Calendar.getInstance(Locale.getDefault())
        calender.timeInMillis = dTime
        val actualTime = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa", calender)
        Log.i("@@@@@@@@: ", "$actualTime")
*/

        // Adding Diary Values Into Database
        val id = databaseHelper.addData(
                ""+txtTitle,
                ""+txtDesc,
                ""+hLight.toString(),
                ""+dTime.toString(),
                ""+txtLocation
        )

        //Toast.makeText(this, "Secret Added $id", Toast.LENGTH_SHORT).show()

    }


}