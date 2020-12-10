package com.example.cityapp

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Identity.NAMESPACE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.ksoap2.serialization.SoapObject


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val NAMESPACE = "http://tempuri.org/";
    private val METHOD_NAME1 = "FahrenheitToCelsius"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn_get_image)
        val cityImage = findViewById<ImageView>(R.id.iv_city_image)

        button.setOnClickListener {
            val y1 = findViewById<EditText>(R.id.y1).text.toString().toInt()
            val x1 = findViewById<EditText>(R.id.x1).text.toString().toInt()
            val y2 = findViewById<EditText>(R.id.y2).text.toString().toInt()
            val x2 = findViewById<EditText>(R.id.x2).text.toString().toInt()
            val url = "/getImage?y1=$y1&x1=$x1&y2=$y2&x2=$x2"
            launch(Dispatchers.Main) {
                val request = SoapObject(NAMESPACE, METHOD_NAME1)

                RetrofitImage.getBitmapFrom(url){
                    cityImage.load(it)
                }

            }
        }
    }
}