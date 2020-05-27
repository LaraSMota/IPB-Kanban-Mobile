package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.data.model.CardModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_createcard)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.CreateCardToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun postDataCard(){
        val post = CardModel(null,
            "First Mobile Card",
            "Primeiro Card Criado Via app no banco",
            null,
            null,
            null,
            null,
            null,
            2)
        val callback = NetworkUtils.request().postCard(post)

        callback.enqueue(object : Callback<CardModel> {
            override fun onFailure(call: Call<CardModel>, t: Throwable) {
                Log.d("postDataCardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<CardModel>, response: Response<CardModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataCardode", "Code: " + response.code())
                    return
                }

//                var postResponse : CardModel? = response.body()
//
//                var content = ""
//                content += "Code: " + response.code() + "\n"
//                content += "ID: " + (postResponse?.board_id ?: "empty") + "\n"
//                content += "Title: " + (postResponse?.title ?: "empty") + "\n"
//                content += "Body: " + (postResponse?.description ?: "empty") + "\n\n"
//
//                text.append(content)
            }
        })
    }
}