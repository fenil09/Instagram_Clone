package com.base.instagram_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var mauth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gotoregister: Button =findViewById(R.id.button2)
        val emaildata:EditText=findViewById(R.id.et1)
        val passdata:EditText=findViewById(R.id.et2)
        val Login:Button=findViewById(R.id.button)
        mauth= FirebaseAuth.getInstance()
        gotoregister.setOnClickListener {
            val intent= Intent(this,Registration::class.java)
            startActivity(intent)
        }
        Login.setOnClickListener {
            val email=emaildata.text.toString()
            val pass=passdata.text.toString()
            LoginUser(email,pass)
        }

    }

    private fun LoginUser(email:String,password:String)= CoroutineScope(Dispatchers.IO).launch {
        mauth.signInWithEmailAndPassword(email,password).await()
        withContext(Dispatchers.Main){
            Toast.makeText(this@MainActivity,"Login Successfull",Toast.LENGTH_SHORT).show()
            delay(2000)
            val intent=Intent(this@MainActivity,Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}