package ise308.orhan_berk_mysecrets.fragment

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.activity.MainActivity
import ise308.orhan_berk_mysecrets.database.DatabaseSecret
import ise308.orhan_berk_mysecrets.util.DatabaseHelper

class UpdateSecretFragment : DialogFragment() {

    private var dbHelper: DatabaseHelper? = null
    lateinit var titleTxt : EditText
    lateinit var descTxt : EditText
    lateinit var addButton : Button
    lateinit var editLocation : EditText
    private var s_id : String? = null
    private var s_title : String? = null
    private var s_desc : String? = null
    lateinit var checkBox: CheckBox
    private var s_hLight : Int? = 0
    private var s_time : String? = null
    private var s_location : String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.activity!!, R.style.DialogTheme)
        val inflater = activity!!.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_update, null)

        dbHelper = DatabaseHelper(activity)
        titleTxt = dialogView.findViewById(R.id.editTitle)
        descTxt = dialogView.findViewById(R.id.editDescription)
        addButton = dialogView.findViewById(R.id.addBTN)
        checkBox = dialogView.findViewById(R.id.checkBox)
        editLocation = dialogView.findViewById(R.id.editLocation)



        getDetails()
        builder.setView(dialogView)

        addButton.setOnClickListener {

            updateSecret()
            (context as MainActivity)!!.onResume()
            dismiss()

        }


        return builder.create()
    }


    fun getDetails(){
        // Getting Details Of Diary From Database
        val selectQuery = "SELECT * FROM ${DatabaseSecret.TABLE_NAME} WHERE ${DatabaseSecret.S_ID} =\"$s_id\""
        val dB = dbHelper!!.writableDatabase
        val cursor = dB.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do {
                val s_id = ""+ cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_ID))
                val title = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_TITLE))
                val desc = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DESCRIPTION))
                val hLight = 0 + cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_HIGHLIGHT))
                val dTime = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DIARY_TIME))
                val dLocation = "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_LOCATION))

                titleTxt.setText(title)
                descTxt.setText(desc)
                editLocation.setText(dLocation)
                s_time = dTime

                // Checking İf It's Highlighted Before
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
        s_location = editLocation.text.toString()

        if (checkBox.isChecked){
            s_hLight = 1
        }
        // Updating Diary Details In Database
        dbHelper?.updateData("$s_id", "$s_title", "$s_desc", "$s_hLight","$s_time", "$s_location")



    }

    // Get Selected Diary
    fun getSecretID(id: String) {
        s_id = id
    }

}