package ise308.orhan_berk_mysecrets.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ise308.orhan_berk_mysecrets.R

class PasswordActivity : AppCompatActivity() {


    // Objects and Variables
    lateinit var passTxt : TextView
    lateinit var pW : EditText
    lateinit var savePassword : Button
    lateinit var newPW : EditText
    lateinit var confirmNewPW : EditText

    var userPass = ""
    var first = 0
    var isPasswordChange = 0

    var newPassword = ""
    var confirmNewPassword = ""
    var oldPassword = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        // Getting Intent Values
        val intent = intent
        isPasswordChange = intent.getIntExtra("change_password", 0)


        passTxt = findViewById(R.id.passwordText)
        pW = findViewById(R.id.pW)
        newPW = findViewById(R.id.newPassword)
        confirmNewPW = findViewById(R.id.confirmPassword)
        savePassword = findViewById(R.id.savePassword)

        // Calling Function
        getFirstTime()
        getPassword()

        if (isPasswordChange == 1) {

            changePassword()

        } else if (first == 0){

            firstTimeOpened()

        } else {

            needToLogin()
        }

    }


    fun setPassword(newPassword : String){
        // Open SharedPreferences
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        // Putting Password Into SharedPreferences
        editor.putString("Password", newPassword)
        editor.apply()
    }

    fun getPassword(){
        // Open SharedPreferences
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        // Getting Password From SharedPreferences
        userPass = prefs.getString("Password", "").toString()
    }


    fun setFirstTime(){
        // Open SharedPreferences
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        // Adding FisrtTime Value Into SharedPreferences
        editor.putInt("firstTime", 1)
        editor.apply()
    }

    fun getFirstTime(){
        // Open SharedPreferences
        val prefs = getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        // Getting FirstTime value from SharedPreferences
        first = prefs.getInt("firstTime", 0)
    }


    fun firstTimeOpened(){

        // Changing Texts
        passTxt.text = getString(R.string.createPass)
        savePassword.text = getString(R.string.saveBtn)

        savePassword.setOnClickListener {

            try{
                // Calling Function with pW Object
                checkNull(pW)

                // Converting pW value into String
                userPass = pW.text.toString()
                setPassword(userPass)
                setFirstTime() // Calling Function

                // Opening New Activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Closing This Activity

            } catch (e : Exception){ // If Anybody Call The Throw Then We Exiting Our Try and Catch with Error
                Toast.makeText(this,getString(R.string.nullSpace), Toast.LENGTH_LONG).show()
            }


        }
    }

    fun needToLogin(){

        passTxt.text = getString(R.string.mainTitle)
        savePassword.text = getString(R.string.enterBtn)
        pW.hint = getString(R.string.enterPass)


        savePassword.setOnClickListener {

            try{
                checkNull(pW)

                if (pW.text.toString().equals(userPass)) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.wrongPass), Toast.LENGTH_LONG)
                            .show()
                }

            } catch (e : Exception){
                Toast.makeText(this,getString(R.string.nullSpace), Toast.LENGTH_LONG).show()
            }


        }


    }

    fun changePassword(){

        passTxt.text = getString(R.string.currentPass)
        pW.hint = getString(R.string.enterPass)

        newPW.visibility = View.VISIBLE
        confirmNewPW.visibility = View.VISIBLE
        savePassword.setText(getString(R.string.changePass))
        savePassword.setOnClickListener {

            newPassword = newPW.text.toString()
            confirmNewPassword = confirmNewPW.text.toString()
            oldPassword = pW.text.toString()


            try{
                checkNull(pW)
                checkNull(confirmNewPW)
                checkNull(newPW)

                // Checking Values is Equal or Not
                if (!oldPassword.equals(userPass)){

                    Toast.makeText(this, getString(R.string.previousPassWrong), Toast.LENGTH_SHORT).show()

                } else if (newPassword.equals(oldPassword) && oldPassword.equals(userPass)) {

                    Toast.makeText(this, getString(R.string.notBeTheSame), Toast.LENGTH_SHORT).show()

                } else if (!newPassword.equals(confirmNewPassword) && userPass.equals(oldPassword)){

                    Toast.makeText(this, getString(R.string.notMatch), Toast.LENGTH_SHORT).show()

                } else if(newPassword.equals(confirmNewPassword) && userPass.equals(oldPassword)) {

                    setPassword(newPassword)
                    Toast.makeText(this, getString(R.string.successChange), Toast.LENGTH_SHORT).show()
                    finish()

                }

            } catch (e : Exception){
                Toast.makeText(this,getString(R.string.nullSpace), Toast.LENGTH_LONG).show()
            }




        }

    }


    fun checkNull(editText : EditText){

            // Checking are there any null/empty EditText Object
        if(editText.text.trim().length == 0){
            throw Exception(getString(R.string.emptySpace))
        }

    }

}