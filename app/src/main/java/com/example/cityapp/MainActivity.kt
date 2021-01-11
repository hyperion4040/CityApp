package com.example.cityapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn_get_image)
        /*button.setOnClickListener {
            val y1 = findViewById<EditText>(R.id.y1).text.toString().toInt()
            val x1 = findViewById<EditText>(R.id.x1).text.toString().toInt()
            val y2 = findViewById<EditText>(R.id.y2).text.toString().toInt()
            val x2 = findViewById<EditText>(R.id.x2).text.toString().toInt()*/
//            val myRequest = myAsyncTask( WeakReference<Activity>(MainActivity()), x1, x2, y1, y2)
            val myRequest = myAsyncTask( WeakReference<ImageView>(findViewById<ImageView>(R.id.iv_city_image)), 35.5, 139.6, 35.8, 139.9)

            myRequest.execute()

//        }

    }
}