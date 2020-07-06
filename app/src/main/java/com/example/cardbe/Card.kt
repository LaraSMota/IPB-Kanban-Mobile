package com.example.cardbe

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import com.example.cardbe.recyclerView.CardItem
import com.example.cardbe.recyclerView.CollumnItem
import com.example.cardbe.recyclerView.DialogDeleteCollumnDealer
import com.example.recyclerviewexample.showDataCardData
import kotlinx.android.synthetic.main.activity_profilescreen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class Card : AppCompatActivity() {
    private var imageData: ByteArray? = null
    var collumnId = 0
    var boardId = "0"
    var cardId = 0
    var labels = "X"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_createcard)
        collumnId = intent.getStringExtra("CollumnId")!!.toInt()
        cardId = intent.getStringExtra("CardId")!!.toInt()

        getDataCard()

        //Set back button and back button color
        val title = findViewById<TextView>(R.id.CreateCardTitle)
        val description = findViewById<TextView>(R.id.CreateCardDescription)
        val comments = findViewById<TextView>(R.id.CreateCardComment)
        val toolbar = findViewById<Toolbar>(R.id.CreateCardToolbar)
        val attachments = findViewById<TextView>(R.id.CreateCardAtachDocument)
        val checklist = findViewById<TextView>(R.id.CreateCardAddChecklist)
        val label = findViewById<TextView>(R.id.CreateCardAddLabels)
        val dueDate = findViewById<TextView>(R.id.CreateCardAddDueDate)
        val saveButton = findViewById<Button>(R.id.CreateCardSave)
        val azul = findViewById<CardView>(R.id.azul)
        val amarelo = findViewById<CardView>(R.id.amarelo)
        val verde = findViewById<CardView>(R.id.verde)
        val vermelho = findViewById<CardView>(R.id.vermelho)
        val blank = findViewById<CardView>(R.id.blank)
        val deleteCard = findViewById<ImageView>(R.id.delteCard)
        var date: String = ""



        setSupportActionBar(toolbar)
        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        deleteCard.visibility = View.VISIBLE
        deleteCard.isEnabled = true

        deleteCard.setOnClickListener{
            val myIntent = Intent(this, DialogDeleteCollumnDealer::class.java)
                .putExtra("From", "Card")
                .putExtra("CardId", cardId.toString())
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(this, myIntent, null)
        }

        dueDate.setOnClickListener{
            val c: Calendar = Calendar.getInstance()
            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // Display Selected date in textbox
                    date = "$monthOfYear, $dayOfMonth"
                }, year, month, day
            )
            dpd.show()
        }

        azul.setOnClickListener{
            labels = "azul"
            azul.setAlpha(1.0f)
            amarelo.setAlpha(0.5f)
            vermelho.setAlpha(0.5f)
            verde.setAlpha(0.5f)
            blank.setAlpha(0.5f)
        }
        amarelo.setOnClickListener{
            labels = "amarelo"
            azul.setAlpha(0.5f)
            amarelo.setAlpha(1.0f)
            vermelho.setAlpha(0.5f)
            verde.setAlpha(0.5f)
            blank.setAlpha(0.5f)
        }
        vermelho.setOnClickListener{
            labels = "vermelho"
            azul.setAlpha(0.5f)
            amarelo.setAlpha(0.5f)
            vermelho.setAlpha(1.0f)
            verde.setAlpha(0.5f)
            blank.setAlpha(0.5f)
        }
        verde.setOnClickListener{
            labels = "verde"
            azul.setAlpha(0.5f)
            amarelo.setAlpha(0.5f)
            vermelho.setAlpha(0.5f)
            verde.setAlpha(1.0f)
            blank.setAlpha(0.5f)
        }
        blank.setOnClickListener{
            labels = "X"
            azul.setAlpha(0.5f)
            amarelo.setAlpha(0.5f)
            vermelho.setAlpha(0.5f)
            verde.setAlpha(0.5f)
            blank.setAlpha(1.0f)
        }


        saveButton.setOnClickListener{
            val cardTitle = title.text.toString()
            val cardDescription = description.text.toString()
            val cardComments = comments.text.toString()

            if(cardTitle.isNotBlank() && cardTitle.isNotEmpty()){
                postDataCard(cardTitle, cardComments, cardDescription)
                onBackPressed()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Make sure to enter a title",
                    Toast.LENGTH_LONG
                ).show()
            }

            attachments.setOnClickListener{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                        //permission denied
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, Profile.PERMISSION_CODE);
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
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun postDataCard(title: String, comments: String, description: String){
        val post = CardModel(cardId,
            title,
            comments,
            null,
            labels,
            null,
            description,
            null,
            collumnId,
            0)
        val callback = NetworkUtils.request().putCard(cardId, post)

        callback.enqueue(object : Callback<CardModel> {
            override fun onFailure(call: Call<CardModel>, t: Throwable) {
                Log.d("postDataCardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<CardModel>, response: Response<CardModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataCardode", "Code: " + response.code())
                    return
                }
            }
        })
    }

    fun getDataCard(){
        val callback = NetworkUtils.request().getCard(cardId)
        callback.enqueue(object : Callback<CardModel> {
            override fun onFailure(call: Call<CardModel>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }


            override fun onResponse(call: Call<CardModel>, response: Response<CardModel>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                val title = findViewById<TextView>(R.id.CreateCardTitle)
                val description = findViewById<TextView>(R.id.CreateCardDescription)
                val comments = findViewById<TextView>(R.id.CreateCardComment)
                labels = response.body()!!.lables.toString()
                title.text = response.body()!!.title.toString()
                description.text = response.body()!!.description.toString()
                comments.text = response.body()!!.comments.toString()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val contexto = this
        val callback = NetworkUtils.request().getCollumn(collumnId)
        callback.enqueue(object : Callback<CollumnModel> {
            override fun onFailure(call: Call<CollumnModel>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<CollumnModel>, response: Response<CollumnModel>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                boardId = response.body()!!.boardId.toString()
                val myIntent = Intent(contexto, Board::class.java)
                    .putExtra("BoardId", boardId.toString())
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(contexto, myIntent, null)
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
        private val PERMISSION_CODE = 1001;
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