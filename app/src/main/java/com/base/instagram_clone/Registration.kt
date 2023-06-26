package com.base.instagram_clone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class Registration : AppCompatActivity() {
    lateinit var mauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val emaildata:EditText=findViewById(R.id.et3)
        val passworddata:EditText=findViewById(R.id.et4)
        val Register=findViewById<Button>(R.id.button3)
        mauth= FirebaseAuth.getInstance()
        Register.setOnClickListener {
            val mail=emaildata.text.toString()
            val password=passworddata.text.toString()
            CreateUser(mail,password)
        }
    }

    private fun CreateUser(email:String,password:String)=CoroutineScope(Dispatchers.IO).launch {
        try{
            mauth.createUserWithEmailAndPassword(email,password).await()
            withContext(Dispatchers.Main){
                Toast.makeText(this@Registration,"Registration successfull",Toast.LENGTH_SHORT).show()
                delay(2000)
                val intent= Intent(this@Registration,Profile_Creation::class.java)
                startActivity(intent)
                finish()
            }
        }
        catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@Registration,e.message,Toast.LENGTH_LONG).show()
            }
        }

    }
}