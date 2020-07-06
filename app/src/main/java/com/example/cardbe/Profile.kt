package com.example.cardbe

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import kotlinx.android.synthetic.main.activity_profilescreen.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException


//PEGAR O ID DO USUARIO LOGADO
//IMPLEMENTAR O ENVIAR FOTO DO USUARIO

class Profile : AppCompatActivity() {
    val userId = 3
    var password = ""
    private var imageData: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilescreen)
        val firstNameInput = findViewById<EditText>(R.id.EditProfileFirstName)
        val lastNameInput = findViewById<EditText>(R.id.EditProfileLastName)
        val emailInput = findViewById<EditText>(R.id.EditProfileEmail)
        val nicknameInput = findViewById<EditText>(R.id.EditProfileNickname)
        val saveButton = findViewById<Button>(R.id.EditProfileSaveButton)
        val profileImageInput = findViewById<ImageView>(R.id.EditProfileProfileImage)
        var firstName = ""
        var lastName = ""
        var email = ""
        var nickname = ""
        var profileImage = R.drawable.ic_person_white_24dp

        getDataUser()

        //Set back button and back button color
        val toolbar = findViewById<Toolbar>(R.id.EditProfileToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        profileImageInput.setOnClickListener{

        }

        saveButton.setOnClickListener {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches() && emailInput.text.toString().isNotEmpty()) {
                if(firstNameInput.text.toString().isNotBlank() && lastNameInput.text.toString().isNotBlank() && nicknameInput.text.toString().isNotBlank()){
                    email = emailInput.text.toString()
                    firstName = firstNameInput.text.toString()
                    lastName = lastNameInput.text.toString()
                    nickname = nicknameInput.text.toString()
                    updateDataUser(firstName, lastName, email, nickname)
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Empty spaces are not allowed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    applicationContext,
                    "Enter a valid e-mail",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        EditProfileProfileImage.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getDataUser() {
        val callback = NetworkUtils.request().getUsers(userId)
        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("getDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (!response.isSuccessful){
                    Log.d("getDataUserCode", "Code: " + response.code())
                    return
                }

                val get = response.body()
                findViewById<EditText>(R.id.EditProfileFirstName).setText(get!!.firstName)
                findViewById<EditText>(R.id.EditProfileLastName).setText(get!!.lastName)
                findViewById<EditText>(R.id.EditProfileEmail).setText(get!!.email)
                findViewById<EditText>(R.id.EditProfileNickname).setText(get!!.nickname)
                password = get!!.password
            }
        })
    }

    fun updateDataUser(firstName: String, lastName: String, email: String, nickname: String){
        val post = UserModel(userId, firstName, lastName, email, nickname, password, "", "true")
        val callback = NetworkUtils.request().putUser(userId, post)

        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("updateDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (!response.isSuccessful){
                    Log.d("updateDataUserCode", "Code: " + response.code())
                    return
                }
                finish()
            }
        })
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val uri = data?.data
            if (uri != null) {
                EditProfileProfileImage.setImageURI(uri)
                createImageData(uri)
            }
        }
    }
}