package com.example.pseudorex.griterianicaragua

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException
import android.database.sqlite.SQLiteException
import android.util.Log
import java.io.File
import java.nio.file.Files.copy
import java.nio.file.Files.exists
import android.content.Context.MODE_PRIVATE
import java.nio.file.Files.exists






class dbHelper(context: Context) : SQLiteOpenHelper(context, "Griteria.db",null,1) {

    //Data from the database
    val mContext = context
    val dbName = "Griteria.db"
    val dbPath = context.applicationInfo.dataDir + "/databases/"
    var needUpdate : Boolean = false



    fun getDatabase(): SQLiteDatabase {
        val mDataBaseExist = checkDataBase()

        //If not exist the database, the code will copy from the assets folder
        if (!mDataBaseExist) {
            copyDatabase()
        }

        return readableDatabase
    }

    fun copyDatabase() {
        try {
            val mInput = mContext.assets.open(dbName)
            // = dbPath + dbName
            val outFileName = mContext.getDatabasePath(dbName)


            //Create if not exists and create if necesary
            if (!outFileName.exists()) {
                val checkDB = mContext.openOrCreateDatabase(dbName, MODE_PRIVATE, null)
                if (checkDB != null) {
                    checkDB!!.close()
                }
            }

            val mOutput = FileOutputStream(outFileName)

            //Copy to the database created
            val mBuffer = ByteArray(1024)
            var mLength: Int


            mLength = mInput.read(mBuffer)
            while (mLength > 0) {
                mOutput.write(mBuffer, 0, mLength)
                mLength = mInput.read(mBuffer)
            }

            //Close buffer
            mOutput.flush()
            mOutput.close()
            mInput.close()

        } catch (e: Throwable) {
            throw RuntimeException("The  database couldn't be installed.", e)
        }

    }

    //Check if exists or not
    fun checkDataBase (): Boolean {
        try {
            val mPath = dbPath + dbName
            val file = File(mPath)
            return if (file.exists())
                true
            else
                false
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return false
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}