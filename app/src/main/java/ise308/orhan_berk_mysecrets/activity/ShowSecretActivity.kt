package ise308.orhan_berk_mysecrets.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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

class ShowSecretActivity : AppCompatActivity() {


    private var dbHelper: DatabaseHelper? = null
    lateinit var titleTxt : TextView
    lateinit var descTxt : TextView
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

        val intent = intent
        s_id = intent.getStringExtra("S_ID")

        getDetails()

    }


    fun getDetails(){

        val selectQuery = "SELECT * FROM ${DatabaseSecret.TABLE_NAME} WHERE ${DatabaseSecret.S_ID} =\"$s_id\""
        val dB = dbHelper!!.writableDatabase
        val cursor = dB.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do {
                val id = ""+ cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_ID))
                val title = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_TITLE))
                val desc = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DESCRIPTION))

                titleTxt.text = title
                descTxt.text = desc
            } while (cursor.moveToNext())
        }
        dB.close()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.searchSecret)
        item.setVisible(false)
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


