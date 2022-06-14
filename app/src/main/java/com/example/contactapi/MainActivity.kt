package com.example.contactapi

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapi.databinding.ActivityMainBinding
import com.example.contactapi.databinding.OpenBinding
import com.example.contactapi.di.ApiState
import com.example.contactapi.di.showMsg
import com.example.contactapi.model.Listeners
import com.example.contactapi.model.MyAdapter
import com.example.contactapi.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),Listeners {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myAdapter= MyAdapter(this)
        setRv()
        setMv()
        setPhoneNo()
    }
    private fun setPhoneNo() {
        binding.apply {
            if (!TextUtils.isEmpty(name.text.toString())&&!TextUtils.isEmpty(phoneNo.text.toString())){
                save.setOnClickListener {
                    lifecycleScope.launchWhenCreated {
                        viewModel.setPhone(name.text.toString(),phoneNo.text.toString().toLong())
                            .catch { e->
                                showMsg(e.message.toString())
                            }.collect{data->
                                showMsg("data loaded successfully")
                                setMv()
                            }
                    }
                }
            }
            else {
               showMsg("please fill all the fields..")
            }
        }
    }

    private fun setMv() {
        lifecycleScope.launchWhenCreated {
            viewModel.getPhone()
            viewModel.apiState.collect{
                when(it){
                    is ApiState.Success->{
                        binding.progressBar.isVisible=false
                        binding.recyclerview.isVisible=true
                        myAdapter.submitList(it.data)
                    }
                    is ApiState.Failure->{
                        binding.progressBar.isVisible=false
                        binding.recyclerview.isVisible=false
                    }
                    is ApiState.Loading->{
                        binding.progressBar.isVisible=true
                        binding.recyclerview.isVisible=false
                    }
                    is ApiState.Empty->{
                    }
                }
            }
        }

    }

    private fun setRv() {
        binding.recyclerview.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=myAdapter
        }
    }

    override fun onClickDelete(position: Int, userId: Int) {
        lifecycleScope.launchWhenCreated {
            viewModel.delete(userId)
                .catch {e->
                    Log.d("error",e.message.toString())
                }
                .collect{
                    showMsg("data deleted successfully")
                    setMv()
                }
        }
    }

    override fun onClickUpdate(position: Int, userId: Int, name: String, Phone: Long) {
        val alertDialog = AlertDialog.Builder(this)
        val binding = OpenBinding.inflate(LayoutInflater.from(this))
        val dialog = alertDialog.create()
        dialog.setView(binding.root)
        binding.apply {
            nameid.setText(name)
            phoneNo.setText("$Phone")
            save.setOnClickListener {
                if (!TextUtils.isEmpty(nameid.text.toString()) && !TextUtils.isEmpty(phoneNo.text.toString())) {
                    lifecycleScope.launchWhenStarted {
                        viewModel.update(
                            userId, nameid.text.toString(),
                            phoneNo.text.toString().toLong()
                        )
                            .catch { e ->
                                Log.d("main", "${e.message}")
                            }.collect {
                                showMsg("update successfully...")
                                setMv()
                            }
                        dialog.dismiss()
                    }
                } else {
                    showMsg("please fill all the field..")
                }
            }
        }
        dialog.show()
    }
}
