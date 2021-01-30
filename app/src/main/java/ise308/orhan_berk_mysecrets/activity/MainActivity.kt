package ise308.orhan_berk_mysecrets.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.database.DatabaseSecret
import ise308.orhan_berk_mysecrets.util.DatabaseHelper
import ise308.orhan_berk_mysecrets.util.HolderAdapter

class MainActivity : AppCompatActivity() {

    var siralamaTuru = 0
    private var recyclerView: RecyclerView? = null
    private val NEWEST_FIRST = "${DatabaseSecret.S_ID} DESC"
    private val FISRT_LAST = "${DatabaseSecret.S_ID} ASC"
    lateinit var dbHelper : DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setTitle(getString(R.string.mainTitle))


        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()


        loadSecret()

    }

    private fun loadSecret(){
        checkOrderBy()
        var holderAdapter = HolderAdapter()
        if (siralamaTuru == 1){
             holderAdapter = HolderAdapter(this, dbHelper.getAllData(FISRT_LAST))
        } else {
             holderAdapter = HolderAdapter(this, dbHelper.getAllData(NEWEST_FIRST))
        }
            recyclerView!!.adapter = holderAdapter
    }

    // Search Function
    private fun searchSecret(query : String){
        // Searching data inside of database
        // We are calling an SQL query
            val holderAdapter = HolderAdapter(this, dbHelper.searchData(query))
            recyclerView!!.adapter = holderAdapter
    }


    // When user click the Delete All Button
    // We Open an dialog and asking are you sure you want to delete.
    private fun deleteAll(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.delAllQuestion)).setPositiveButton(getString(R.string.delete)){
            dialogInterface, i ->   dbHelper.deleteAllSecrets()
            onResume()
        }.setNegativeButton(getString(R.string.cancel)) {
            dialogInterface, i ->
        }.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.searchSecret)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    searchSecret(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    searchSecret(query)
                }
                return true
            }

        } )

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
                true
            }

            R.id.deleteAllSecrets -> {

                deleteAll()
                true
            }

            R.id.openSettings -> {

                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }



    // When user press android back button
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        //overridePendingTransition(R.anim.slide_in, R.anim.out)
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            //Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


    public override fun onResume() {
        super.onResume()
        loadSecret()
    }

    // Getting " How To Order By " Value From SharedPreference
    fun checkOrderBy(){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        siralamaTuru = prefs.getInt("orderBy", 0)
    }

}