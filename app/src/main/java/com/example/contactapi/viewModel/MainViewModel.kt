package com.example.contactapi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapi.di.ApiState
import com.example.contactapi.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository):ViewModel() {

    val _apiState:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val apiState:StateFlow<ApiState> = _apiState
    fun getPhone()=viewModelScope.launch {
        repository.getPhone().onStart {
            _apiState.value=ApiState.Empty
        }
            .catch { e->
            _apiState.value=ApiState.Failure(e)
        }
            .collect {
            _apiState.value=ApiState.Success(it)
        }
    }

    fun setPhone(name:String,PhoneNo:Long)=repository.setPhone(name,PhoneNo)

    fun delete(userId:Int)=repository.delete(userId)

    fun update(userId: Int,name:String,Phone:Long)
    =repository.update(userId, name, Phone)
}