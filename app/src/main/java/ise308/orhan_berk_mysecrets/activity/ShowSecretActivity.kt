package ise308.orhan_berk_mysecrets.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.database.DatabaseSecret
import ise308.orhan_berk_mysecrets.util.DatabaseHelper
import ise308.orhan_berk_mysecrets.util.HolderAdapter
import java.util.*

class ShowSecretActivity : AppCompatActivity() {


    // Objects and Variables
    private var dbHelper: DatabaseHelper? = null
    lateinit var titleTxt : TextView
    lateinit var descTxt : TextView
    lateinit var diaryDate : TextView
    lateinit var diaryLocation : TextView
    private var s_id : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.showSecret))

        dbHelper = DatabaseHelper(this)
        titleTxt = findViewById(R.id.showTitle)
        descTxt = findViewById(R.id.showDesc)
        diaryDate = findViewById(R.id.textDate)
        diaryLocation = findViewById(R.id.textLocation)

        // Getting Intent Value
        val intent = intent
        s_id = intent.getStringExtra("S_ID")

        // Calling Function
        getDetails()

    }


    fun getDetails(){

        // Getting S_ID's values from database
        val selectQuery = "SELECT * FROM ${DatabaseSecret.TABLE_NAME} WHERE ${DatabaseSecret.S_ID} =\"$s_id\""
        val dB = dbHelper!!.writableDatabase
        val cursor = dB.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do {
                // Adding database values to variables.
                val id = ""+ cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_ID))
                val dTime = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DIARY_TIME))
                titleTxt.text = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_TITLE))
                descTxt.text = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DESCRIPTION))
                diaryDate.text = DateFormat.format("dd/MM/yyyy", Date(dTime.toLong())).toString()
                diaryLocation.text = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_LOCATION))
            } while (cursor.moveToNext())
        }
        dB.close()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // ActionBar Menu Buttons
        val item = menu.findItem(R.id.searchSecret)
        item.setVisible(false) // Hide The Buttons
        val item2 = menu.findItem(R.id.deleteAllSecrets)
        item2.setVisible(false)
        val item3 = menu.findItem(R.id.openSettings)
        item3.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            R.id.addNewSecret -> {

                // Opening The AddSecret Activity
                val intent = Intent(this, AddSecretActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Toolbar Back Button Function
    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

}


