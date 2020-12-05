package com.example.cityapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn_get_image)
        val city_image = findViewById<ImageView>(R.id.iv_dog_image)
        button.setOnClickListener {
            // Use launch and pass Dispatchers.Main to tell that
            // the result of this Coroutine is expected on the main thread.
            launch(Dispatchers.Main) {

                RetrofitImage.getBitmapFrom("/getImage?y1=0&x1=0&y2=200&x2=200"){
                    city_image.load(it)
                }
                // Try catch block to handle exceptions when calling the API.
               /* try {
                    val response = ApiAdapter.apiClient.getImage()
                    // Check if response was successful
                    if (response.isSuccessful && response.body() != null) {
                        // Retrieve data.
                        val data = response.body()!!
                        data.message?.let {
                            // Load URL into the ImageView using Coil.
//                            val bitmap = BitmapFactory.decodeByteArray(data,0,data.size)
                            city_image.load(it)
                        }
                    } else {
                        // Show API error.
                        // This is when the server responded with an error.
                        Toast.makeText(
                                this@MainActivity,
                                "Error Occurred: ${response.message()}",
                                Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    // Show API error. This is the error raised by the client.
                    // The API probably wasn't called in this case, so better check before assuming.
                    Toast.makeText(this@MainActivity,
                            "Error Occurred: ${e.message}",
                            Toast.LENGTH_LONG).show()
                }*/
            }
        }
    }
}