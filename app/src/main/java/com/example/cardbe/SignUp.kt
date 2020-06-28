package com.example.cardbe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import com.example.cardbe.information.Terms
import com.example.cardbe.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//PRECISA TRATAR PARA:
//NÃO ADIMITIR DUAS CONTAS COM O MESMO EMAIL
//CRIPTOGRAFAR SENHA
//NÃO ADMITIR NUMEROS NO FIRST E LASTNAME

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_singup)

        val firstName = findViewById<EditText>(R.id.SignUpFirstName)
        val lastName = findViewById<EditText>(R.id.SignUpLastName)
        val email = findViewById<EditText>(R.id.SignUpEmail)
        val confirmEmail = findViewById<EditText>(R.id.SignUpConfirmEmail)
        val nickname = findViewById<EditText>(R.id.SignUpNickname)
        val password = findViewById<EditText>(R.id.SignUpPassword)
        val signUpButton = findViewById<Button>(R.id.SignUpSignUpButton)
        val termBox = findViewById<CheckBox>(R.id.SignUpTermsCheckBox)
        val termLink = findViewById<TextView>(R.id.SingUpTermsLink)
        var isValid = false
        signUpButton.isEnabled = false

        termLink.setOnClickListener{
            startActivity(Intent(this, Terms::class.java))
        }

        termBox.setOnClickListener{
            signUpButton.isEnabled = termBox.isChecked
        }

        signUpButton.setOnClickListener {
            val isFill = firstName.text.isNotBlank() && lastName.text.isNotBlank() && email.text.isNotBlank() && confirmEmail.text.isNotBlank() && nickname.text.isNotBlank() && password.text.isNotBlank()
            if (!isFill){
                Toast.makeText(
                    applicationContext,
                    "Please, fill all the spaces to continue",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                if(!isValid){
                    Toast.makeText(
                        applicationContext,
                        "Enter a valid e-mail adress",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if(!email.text.toString().equals(confirmEmail.text.toString())){
                        Toast.makeText(
                            applicationContext,
                            "Different e-mails, please check ",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        password.setText(password.text.toString().replace(" ",""), TextView.BufferType.EDITABLE)
                        if(password.text.toString().length > 6 && password.text.toString().isNotBlank()){
                            val formPassword = password.text.toString()
                            val formFirsName = firstName.text.toString()
                            val formLastName = lastName.text.toString()
                            val formEmail = email.text.toString()
                            val formNickname = nickname.text.toString()
                            postDataUser(formFirsName, formLastName, formEmail, formNickname, formPassword)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                R.string.invalid_password,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    fun postDataUser(fistName: String, lastName: String, email: String, nickname: String, password: String){
        val post = UserModel(null, fistName, lastName, email, nickname, password, null, "true")
        val callback = NetworkUtils.request().postUser(post)

        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("postDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataUserCode", "Code: " + response.code())
                    return
                }
                Toast.makeText(
                    applicationContext,
                    "Sign Up finished",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }
}