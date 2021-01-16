package ise308.orhan_berk_mysecrets.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ise308.orhan_berk_mysecrets.R

class SettingsActivity : AppCompatActivity() {

    lateinit var color1 : RadioButton
    lateinit var color2 : RadioButton
    lateinit var color3 : RadioButton
    lateinit var color4 : RadioButton
    lateinit var asc : RadioButton
    lateinit var desc : RadioButton

    var seciliRenk = 0
    var orderBy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(getString(R.string.settings))

        getSharedPref()

        color1 = findViewById(R.id.hColor1)
        color2 = findViewById(R.id.hColor2)
        color3 = findViewById(R.id.hColor3)
        color4 = findViewById(R.id.hColor4)

        asc = findViewById(R.id.asc)
        desc = findViewById(R.id.desc)

        when(orderBy){
            0 -> desc.isChecked = true
            1 -> asc.isChecked = true
        }

        desc.setOnClickListener {
            orderBySelected(0)
        }

        asc.setOnClickListener {
            orderBySelected(1)
        }

        when (seciliRenk) {
            1 -> color1.isChecked = true
            2 -> color2.isChecked = true
            3 -> color3.isChecked = true
            4 -> color4.isChecked = true
        }

        color1.setOnClickListener {
            sethLightColor(1)
        }

        color2.setOnClickListener {
            sethLightColor(2)
        }

        color3.setOnClickListener {
            sethLightColor(3)
        }

        color4.setOnClickListener {
            sethLightColor(4)
        }

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
        onResume()
        finish()
        return true
    }

    fun sethLightColor(seciliRenk : Int){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("highLightRenk", seciliRenk)
        editor.apply()
    }

    fun orderBySelected(selectedOrder : Int){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("orderBy", selectedOrder)
        editor.apply()
    }

    fun getSharedPref(){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        seciliRenk = prefs.getInt("highLightRenk", 0)
        orderBy = prefs.getInt("orderBy", 0)
    }
}