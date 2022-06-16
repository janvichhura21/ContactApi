package com.example.contactapi.model

import android.database.sqlite.SQLiteOpenHelper

interface Listeners {
    fun onClickDelete(position:Int,userId:Int)

    fun onClickUpdate(position:Int,userId:Int,name:String,Phone:Long)
}

