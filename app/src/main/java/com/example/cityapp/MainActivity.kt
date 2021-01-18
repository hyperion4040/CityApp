package com.example.cityapp

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
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
        button.setOnClickListener {
            val y1Text =   findViewById<EditText>(R.id.y1)
            val x1Text =   findViewById<EditText>(R.id.x1)
            val y2Text =   findViewById<EditText>(R.id.y2)
            val x2Text =   findViewById<EditText>(R.id.x2)
            val y1 = y1Text.text.toString().toFloat()
            val x1 = x1Text.text.toString().toFloat()
            val y2 = y2Text.text.toString().toFloat()
            val x2 = x2Text.text.toString().toFloat()
           y1Text.onEditorAction(EditorInfo.IME_ACTION_DONE);
           x1Text.onEditorAction(EditorInfo.IME_ACTION_DONE);
           y2Text.onEditorAction(EditorInfo.IME_ACTION_DONE);
           x2Text.onEditorAction(EditorInfo.IME_ACTION_DONE);
            val myRequest = myAsyncTask( WeakReference(findViewById(R.id.iv_city_image)),WeakReference(findViewById(R.id.iv_city_minimap)), y1, x1, y2, x2)
            /*val myRequest = myAsyncTask( WeakReference(findViewById(R.id.iv_city_image)),WeakReference(findViewById(R.id.iv_city_minimap)),
                    35.54, 139.61, 35.62, 139.92)*/

            myRequest.execute()

        }

    }
}