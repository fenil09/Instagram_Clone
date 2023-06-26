package com.base.instagram_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gotoregister: Button =findViewById(R.id.button2)
        gotoregister.setOnClickListener {
            val intent= Intent(this,Registration::class.java)
            startActivity(intent)
        }
    }
}