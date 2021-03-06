package ise308.orhan_berk_mysecrets.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ise308.orhan_berk_mysecrets.database.DatabaseSecret

class DatabaseHelper(context : Context?) : SQLiteOpenHelper (context, DatabaseSecret.DB_NAME, null, DatabaseSecret.DB_VERSION){


    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(DatabaseSecret.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, db1: Int, db2: Int) {
        // Delete Table
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseSecret.TABLE_NAME)
        onCreate(db)
    }

    // Delete Selected Diary
    fun deleteSecret(id : String){
        val dB = writableDatabase
        dB.delete(DatabaseSecret.TABLE_NAME, "${DatabaseSecret.S_ID} = ?", arrayOf(id))
        dB.close()
    }

    // Delete All Diaries
    fun deleteAllSecrets(){
        val dB = writableDatabase
        dB.execSQL("DELETE FROM ${DatabaseSecret.TABLE_NAME}")
        dB.close()

    }

    // Add New Diary
    fun addData(title: String?, description : String?, hLight : String?, diaryTime : String?, diaryLocation : String?): Long{

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DatabaseSecret.S_TITLE, title)
        values.put(DatabaseSecret.S_DESCRIPTION, description)
        values.put(DatabaseSecret.S_HIGHLIGHT, hLight)
        values.put(DatabaseSecret.S_DIARY_TIME, diaryTime)
        values.put(DatabaseSecret.S_LOCATION, diaryLocation)
        val sid = db.insert(DatabaseSecret.TABLE_NAME, null, values)
        db.close()
        return sid

    }

    // Update Existed Diary with New Data
    fun updateData(id : String, title : String?, desc : String?, hLight: String?, diaryTime : String?, diaryLocation : String?) : Long{
        val dB = this.writableDatabase
        val values = ContentValues()
        values.put(DatabaseSecret.S_TITLE, title)
        values.put(DatabaseSecret.S_DESCRIPTION, desc)
        values.put(DatabaseSecret.S_HIGHLIGHT, hLight)
        values.put(DatabaseSecret.S_DIARY_TIME, diaryTime)
        values.put(DatabaseSecret.S_LOCATION, diaryLocation)
        return dB.update(DatabaseSecret.TABLE_NAME, values, "${DatabaseSecret.S_ID}=?", arrayOf(id)).toLong()

    }

    // Getting All Data Inside Of Database
    fun getAllData(orderBy : String) : ArrayList<HolderStructure>{
        val secretList = ArrayList<HolderStructure>()
        val selectQuery = "SELECT * FROM ${DatabaseSecret.TABLE_NAME} ORDER BY $orderBy"
        val dB = this.readableDatabase
        val cursor = dB.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                val hStructure = HolderStructure(
                        "" + cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_TITLE)),
                        "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DESCRIPTION)),
                        ""+ cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_HIGHLIGHT)),
                        ""+ cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DIARY_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_LOCATION))
                )
                secretList.add(hStructure)
            } while (cursor.moveToNext())
        }
        dB.close()
        return secretList
    }


    // Search Data Inside Of Database
    fun searchData(query: String) : ArrayList<HolderStructure>{
        val secretList = ArrayList<HolderStructure>()
        val selectQuery = "SELECT * FROM ${DatabaseSecret.TABLE_NAME} WHERE ${DatabaseSecret.S_TITLE} LIKE'%$query%'"
        val dB = this.writableDatabase
        val cursor = dB.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                val hStructure = HolderStructure(
                        "" + cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_TITLE)),
                        "" + cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DESCRIPTION)),
                        ""+ cursor.getInt(cursor.getColumnIndex(DatabaseSecret.S_HIGHLIGHT)),
                        ""+ cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_DIARY_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(DatabaseSecret.S_LOCATION))
                )
                secretList.add(hStructure)
            } while (cursor.moveToNext())
        }
        dB.close()
        return secretList
    }

}