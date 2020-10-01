package com.dm.okhttpdemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainViewModel : ViewModel() {

    private val client: OkHttpClient = OkHttpClient()

    private var _answer = MutableLiveData<Answer>().also {
        requestYesOrNo()
    }

    val answer: LiveData<Answer>
        get() = _answer

    fun requestYesOrNo() {
        val request = Request.Builder()
            .url("https://yesno.wtf/api")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("RESPONSE", "failed - ${e.message}")
                //Toast.makeText(this@MainActivity, "Failed try again", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
//                Log.d("RESPONSE", response.body?.string() ?: "null")

                _answer.postValue(Gson().fromJson<Answer>(response.body?.string(), Answer::class.java))
            }
        })
    }
}

data class Answer(val answer: String, val forced: Boolean, val image: String)