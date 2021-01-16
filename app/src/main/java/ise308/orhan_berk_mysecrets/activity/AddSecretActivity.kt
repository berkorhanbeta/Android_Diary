package ise308.orhan_berk_mysecrets.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.util.DatabaseHelper

class AddSecretActivity : AppCompatActivity() {

    lateinit var databaseHelper : DatabaseHelper
    lateinit var addTitle : EditText
    lateinit var addDescription : EditText
    lateinit var addButton : Button
    lateinit var highLight : CheckBox

    var txtTitle = ""
    var txtDesc = ""
    var hLight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.mAdd))

        databaseHelper = DatabaseHelper(this)

        addTitle = findViewById(R.id.addTitle)
        addDescription = findViewById(R.id.addDescription)
        addButton = findViewById(R.id.addBTN)
        highLight = findViewById(R.id.checkBox)


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

        txtTitle = addTitle.text.toString()
        txtDesc = addDescription.text.toString()

        if (highLight.isChecked){
            hLight = 1
        }

        val id = databaseHelper.addData(
                ""+txtTitle,
                ""+txtDesc,
                ""+hLight.toString()
        )

        //Toast.makeText(this, "Secret Added $id", Toast.LENGTH_SHORT).show()

    }


}