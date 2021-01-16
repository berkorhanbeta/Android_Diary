package ise308.orhan_berk_mysecrets.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.database.DatabaseSecret
import ise308.orhan_berk_mysecrets.util.DatabaseHelper

class UpdateSecretActivity : AppCompatActivity() {


    private var dbHelper: DatabaseHelper? = null
    lateinit var titleTxt : EditText
    lateinit var descTxt : EditText
    lateinit var addButton : Button
    lateinit var checkBox: CheckBox
    private var s_id : String? = null
    private var s_title : String? = null
    private var s_desc : String? = null
    private var s_hLight : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle("Show Secret")

        dbHelper = DatabaseHelper(this)
        titleTxt = findViewById(R.id.addTitle)
        descTxt = findViewById(R.id.addDescription)
        addButton = findViewById(R.id.addBTN)
        checkBox = findViewById(R.id.checkBox)

        val intent = intent
        s_id = intent.getStringExtra("S_ID")


        getDetails()

        addButton.setOnClickListener {

            updateSecret()

        }

    }


    fun getDetails(){

        val selectQuery = "SELECT * FROM ${DatabaseSecret.TABLE_NAME} WHERE ${DatabaseSecret.S_ID} =\"$s_id\""
        val dB = dbHelper!!.writableDatabase
        val cursor = dB.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do {
                val s_id = ""+ cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_ID))
                val title = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_TITLE))
                val desc = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DESCRIPTION))
                val hLight = 0 + cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_HIGHLIGHT))

                titleTxt.setText(title)
                descTxt.setText(desc)
                if (hLight == 1){
                    checkBox.isChecked = true
                }

            } while (cursor.moveToNext())
        }
        dB.close()
    }


    fun updateSecret(){

        s_title = titleTxt.text.toString()
        s_desc = descTxt.text.toString()
        if (checkBox.isChecked){
            s_hLight = 1
        }

        dbHelper?.updateData("$s_id", "$s_title", "$s_desc", "$s_hLight")

        //Toast.makeText(this, "Secret Added $s_id", Toast.LENGTH_SHORT).show()

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
