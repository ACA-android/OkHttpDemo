package com.dm.okhttpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestYesOrNo()

        imageView.setOnClickListener {
            requestYesOrNo()
        }
    }

    private fun requestYesOrNo() {
        val request = Request.Builder()
                .url("https://yesno.wtf/api")
                .build()

//        GlobalScope.launch(Dispatchers.IO) {
//            val response: Response = client.newCall(request).execute()
//            Log.d("RESPONSE", response.body?.string() ?: "null")
//        }

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("RESPONSE", "failed - ${e.message}")
                Toast.makeText(this@MainActivity, "Failed try again", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
//                Log.d("RESPONSE", response.body?.string() ?: "null")
                val answer = Gson().fromJson<Answer>(response.body?.string(), Answer::class.java)
                GlobalScope.launch(Dispatchers.Main) {
                    textView.text = answer.answer
                    Glide.with(this@MainActivity)
                            .load(answer.image)
                            .into(imageView)
                }
            }
        })
    }
}

data class Answer(val answer: String, val forced: Boolean, val image: String)