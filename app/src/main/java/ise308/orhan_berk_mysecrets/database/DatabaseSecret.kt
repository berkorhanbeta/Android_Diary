package ise308.orhan_berk_mysecrets.database

object DatabaseSecret {

    const val DB_NAME = "MY_SECRETS"
    const val DB_VERSION = 1

    const val TABLE_NAME = "SECRETS"
    const val S_ID = "ID"
    const val S_TITLE = "TITLE"
    const val S_DESCRIPTION = "DESCRIPTION"
    const val S_HIGHLIGHT = "HIGHLIGHT"
    const val S_DIARY_TIME = "TIME"
    const val S_LOCATION = "LOCATION"

    // Creating Database with Structure
    const val CREATE_TABLE = (

            "CREATE TABLE " + TABLE_NAME + "(" +
                    S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    S_TITLE + " TEXT," +
                    S_DESCRIPTION + " TEXT," +
                    S_HIGHLIGHT + " INTEGER," +
                    S_DIARY_TIME + " TEXT," +
                    S_LOCATION + " TEXT" + ")"
            )

}