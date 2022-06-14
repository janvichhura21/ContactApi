package com.example.contactapi.model

interface Listeners {
    fun onClickDelete(position:Int,userId:Int)

    fun onClickUpdate(position:Int,userId:Int,name:String,Phone:Long)
}