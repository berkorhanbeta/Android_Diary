package ise308.orhan_berk_mysecrets.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ise308.orhan_berk_mysecrets.R

class PasswordActivity : AppCompatActivity() {

    lateinit var passTxt : TextView
    lateinit var pW : EditText
    lateinit var savePassword : Button

    var userPass = ""
    var first = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        passTxt = findViewById(R.id.passwordText)
        pW = findViewById(R.id.pW)
        savePassword = findViewById(R.id.savePassword)

        getFirstTime()

        if(first == 1){
            passTxt.text = getString(R.string.enterPass)
            savePassword.text = getString(R.string.saveBtn)
            savePassword.text = getString(R.string.enterPass)
        }

        savePassword.setOnClickListener {

            if (first == 0){
                userPass = pW.text.toString()
                setPassword(userPass)
                setFirstTime()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                getPassword()
                if(pW.text.toString().equals(userPass)){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,getString(R.string.wrongPass),Toast.LENGTH_LONG).show()
                }
            }

        }

    }


    fun setPassword(newPassword : String){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("Password", newPassword)
        editor.apply()
    }

    fun getPassword(){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        userPass = prefs.getString("Password", "").toString()
    }


    fun setFirstTime(){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("firstTime", 1)
        editor.apply()
    }

    fun getFirstTime(){
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        first = prefs.getInt("firstTime", 0)
    }


}