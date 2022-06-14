package com.example.contactapi.repository

import com.example.contactapi.model.Phone
import com.example.contactapi.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    fun getPhone():Flow<List<Phone>> = flow {
        emit(apiService.getPhone())
    }.flowOn(Dispatchers.IO)


    fun setPhone(name:String,Phone:Long):Flow<Phone>
            = flow {
        emit(apiService.setPhone(name,Phone))
    }.flowOn(Dispatchers.IO)

    fun delete(userId:Int):Flow<Response<Unit>> = flow {
        emit(apiService.delete(userId))
    }.flowOn(Dispatchers.IO)

    fun update(userId: Int,name:String,Phone:Long):Flow<Response<Unit>>
    = flow {
        emit(apiService.updatePhone(userId,name,Phone))
    }.flowOn(Dispatchers.IO)
}