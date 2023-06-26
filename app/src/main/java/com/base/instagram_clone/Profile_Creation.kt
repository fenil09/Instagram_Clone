package com.base.instagram_clone

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Profile_Creation : AppCompatActivity() {
    lateinit var mauth:FirebaseAuth
    lateinit var circularimage:CircleImageView
    var profileuri: Uri? =null
    val reqcode:Int=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_creation)
        circularimage=findViewById(R.id.circle1)
        val createprofile:Button=findViewById(R.id.button4)
        val name:EditText=findViewById(R.id.et5)
        mauth= FirebaseAuth.getInstance()
        circularimage.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type="image/*"
                startActivityForResult(it,reqcode)
            }
        }
        createprofile.setOnClickListener {
            val username=name.text.toString()
            profileuri?.let { it1 -> SetupUser(profileimageUri = it1, username = username) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==reqcode){

            data?.data?.let {
                profileuri=it
            }
        }
    }

    private fun SetupUser(profileimageUri: Uri,username:String)=CoroutineScope(Dispatchers.IO).launch {
        val profileupdates=UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(profileimageUri)
            .build()
        mauth.currentUser?.let {
            it.updateProfile(profileupdates).await()
            withContext(Dispatchers.Main){
                circularimage.setImageURI(it.photoUrl)
                Toast.makeText(this@Profile_Creation,"User Profile setuped successfully",Toast.LENGTH_LONG).show()
            }
        }
    }

}