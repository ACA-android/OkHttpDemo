package com.dm.okhttpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.answer.observe(this, Observer { answer ->
                textView.text = answer.answer
                Glide.with(this@MainActivity)
                    .load(answer.image)
                    .into(imageView)
        })

        imageView.setOnClickListener {
            viewModel.requestYesOrNo()
        }
    }
}