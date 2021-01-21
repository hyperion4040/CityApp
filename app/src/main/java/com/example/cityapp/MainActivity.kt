package com.example.cityapp

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<View>(R.id.iv_city_image).setOnTouchListener(handleTouch);
        val myRequest = myAsyncTask(WeakReference(findViewById(R.id.iv_city_image)), WeakReference(findViewById(R.id.iv_city_minimap)),
                35.54, 139.61, 35.62, 139.92)
        myRequest.execute()
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
            val myRequest = myAsyncTask(WeakReference(findViewById(R.id.iv_city_image)), WeakReference(findViewById(R.id.iv_city_minimap)), y1, x1, y2, x2)
            /*val myRequest = myAsyncTask( WeakReference(findViewById(R.id.iv_city_image)),WeakReference(findViewById(R.id.iv_city_minimap)),
                    35.54, 139.61, 35.62, 139.92)*/

            myRequest.execute()

        }

    }

    private val handleTouch: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            val x = event.x.toInt()
            val y = event.y.toInt()

            val screenX = event.rawX
            val screenY = event.rawY
            val viewX1 = screenX - 377
            val viewY1 = screenY - 249

            val view = findViewById<View>(R.id.iv_city_image)


            var y1 = 35.82
            var x1 = 139.61
            var x2 = 139.92
            var y2 = 35.54
            var finalX1 = 0.0
            var finalY1 = 0.0
            var finalY2 = 0.0
            var finalX2 = 0.0
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val rawX = event.x.toInt() - 379
                    val rawY = event.y.toInt() - 41
                    finalX1 = (x2- x1) * rawX / (701-379) + x1
                    finalY1 = (y1-y2) * rawY / (363-43) + y2

                }


                MotionEvent.ACTION_UP -> {
                    val rawX = event.x.toInt() - 377
                    val rawY = event.y.toInt() - 41
                    finalX2 = (x2- x1) * rawX / (701-379) + x1
                    finalY2 = (y1-y2) * rawY / (363-43) + y2

                    myAsyncTask(WeakReference(findViewById(R.id.iv_city_image)), WeakReference(findViewById(R.id.iv_city_minimap)),
                            finalY1, finalX1, finalY2, finalX2).execute()
                }

            }
            return true
        }
    }



}