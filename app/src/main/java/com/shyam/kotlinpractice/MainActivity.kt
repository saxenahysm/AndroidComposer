package com.shyam.kotlinpractice

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shyam.kotlinpractice.helperClasses.ApiInterface
import com.shyam.kotlinpractice.helperClasses.RetrofitClient
import com.shyam.kotlinpractice.models.ResponseListUsers

class MainActivity : AppCompatActivity() {
    lateinit var txtData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtData = findViewById(R.id.txtData)
        getUserList()
    }

    private fun getUserList() {
        var retrofit = RetrofitClient.getInstance()
        var apiInterface = retrofit.create(ApiInterface::class.java)
        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getAllUsers()
                if (response.isSuccessful) {
                    Log.e("TAG111", "getUserList: " + response.body())
                    val model = response.body()?.data
                    txtData.text = model?.get(0)?.firstName

                } else {
                    Toast.makeText(
                        this@MainActivity, response.errorBody().toString(), Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
            }
        }
    }
}