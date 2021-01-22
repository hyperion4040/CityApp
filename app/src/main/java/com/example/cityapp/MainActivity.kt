package com.example.cityapp

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private var finalX1 = 0.0
    private var finalY1 = 0.0
    private var finalY2 = 0.0
    private var finalX2 = 0.0
    private var left1 = 1
    private var top1 = 1

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        findViewById<View>(R.id.iv_city_image).setOnTouchListener(handleTouch)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = this.layoutInflater.inflate(R.layout.activity_main, null) as LinearLayout

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        mainLayout.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mainLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        } else {
                            mainLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                        }
                        val imageView = findViewById<View>(R.id.iv_city_image)
                        val rectf = Rect()
                        imageView.getGlobalVisibleRect(rectf);
                        val locations = IntArray(2)
                        imageView.getLocationOnScreen(locations)
                        left1 = locations[0]
                        top1 = locations[1]
                        // measure your views here
                    }
                }
        )

//        setContentView(R.layout.activity_main)
        setContentView(mainLayout)


        val myRequest = simpleAsyncTask(WeakReference(
                findViewById(R.id.iv_city_image)),
                35.54, 139.61, 35.82, 139.92)
        myRequest.execute()
        val button = findViewById<Button>(R.id.btn_get_image)
        button.setOnClickListener {

            val y1 = getCoordinateFrom(R.id.y1, R.string.y1Coordinate)
            val x1 = getCoordinateFrom(R.id.x1, R.string.x1Coordinate)
            val y2 = getCoordinateFrom(R.id.y2, R.string.y2Coordinate)
            val x2 = getCoordinateFrom(R.id.x2, R.string.x2Coordinate)
            myAsyncTask(WeakReference(
                    findViewById(R.id.iv_city_image)),
                    WeakReference(findViewById(R.id.iv_city_minimap)),
                    y1, x1, y2, x2).execute()

        }

    }

    private fun getCoordinateFrom(editTextSpecifier: Int, defaultValue: Int): Float {
        val editText = findViewById<EditText>(editTextSpecifier)
        editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        return ifBlankUseDefault(editText.text.toString(),resources.getString(defaultValue) )
    }

    private fun ifBlankUseDefault(value: String, defaultValue: String): Float {
        return if (value.isBlank()) {
            defaultValue.toFloat()
        } else
            value.toFloat()
    }

    private val handleTouch: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {

            val y1 = 35.82
            val x1 = 139.61
            val x2 = 139.92
            val y2 = 35.54

            /*  val rectf = Rect()
              val imageView = findViewById<View>(R.id.iv_city_image)
              imageView.getGlobalVisibleRect(rectf);
              Log.i("width: ", "${rectf.width()}")
              Log.i("height: ", "${rectf.height()}")
              Log.i("top: ", "${rectf.top}")
              Log.i("bottom: ", "${rectf.bottom}")
              Log.i("left: ", "${rectf.left}")
              Log.i("right: ", "${rectf.right}")*/
            Log.i("Top", "$top1")
            Log.i("left", "$left1")
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val rawX = event.x.toInt() - 377
                    val rawY = event.y.toInt() - 41
                    finalX1 = (x2 - x1) * rawX / (701 - 379) + x1
                    finalY1 = (y1 - y2) * ((363 - 41) - rawY) / (363 - 41) + y2

                }

                MotionEvent.ACTION_UP -> {
                    val rawX = event.x.toInt() - 377
                    val rawY = event.y.toInt() - 41
                    finalX2 = (x2 - x1) * rawX / (701 - 379) + x1
                    finalY2 = (y1 - y2) * ((363 - 41) - rawY) / (363 - 41) + y2

                    Log.i("Cooridnate", "y1 = $finalY1, x1 = $finalX1, y2 = $finalY2, x2 = $finalX2")
                    myAsyncTask(WeakReference(findViewById(R.id.iv_city_image)), WeakReference(findViewById(R.id.iv_city_minimap)),
                            finalY1, finalX1, finalY2, finalX2).execute()
                }

            }
            return true
        }
    }


}