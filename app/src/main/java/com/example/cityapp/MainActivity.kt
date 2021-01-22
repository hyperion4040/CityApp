package com.example.cityapp

import android.graphics.Point
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
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.ref.WeakReference
import kotlin.math.max
import kotlin.math.min


private const val leftCoordinate = 379


private const val rightCoordinate = 701

private const val topCoordinate = 41

private const val bottomCoordinate = 363

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {


    private val yMax = 35.82
    private val xMin = 139.61
    private val xMax = 139.92
    private val yMin = 35.54

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

            val y1 = getCoordinateFrom(R.id.y1, R.string.y1Coordinate, R.string.y2Coordinate)
            val x1 = getCoordinateFrom(R.id.x1, R.string.x1Coordinate, R.string.x2Coordinate)
            val y2 = getCoordinateFrom(R.id.y2, R.string.y2Coordinate, R.string.y1Coordinate)
            val x2 = getCoordinateFrom(R.id.x2, R.string.x2Coordinate, R.string.x1Coordinate)
            val yValues = coordinatesVerification(y1, y1, "")
            val xValues = coordinatesVerification(x1,x2,"x")
            myAsyncTask(WeakReference(
                    findViewById(R.id.iv_city_image)),
                    WeakReference(findViewById(R.id.iv_city_minimap)),
                    yValues[0], xValues[0], yValues[1], xValues[1]).execute()

        }

    }

    private fun coordinatesVerification(value1: Float, value2: Float, coordinate: String): List<Float> {

        return if(coordinate == "x"){
            when {
                value1 == value2 -> listOf(xMin.toFloat(),xMax.toFloat())
                else -> listOf(value1,value2)
            }
        }else {
            when {
                value1 == value2 -> listOf(yMin.toFloat(),yMax.toFloat())
                else -> listOf(value1,value2)
            }
         }

    }

    private fun getCoordinateFrom(editTextSpecifier: Int, defaultValue: Int, secondDefaultValue: Int): Float {
        val editText = findViewById<EditText>(editTextSpecifier)
        editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        return ifBlankUseDefault(editText.text.toString(), resources.getString(defaultValue), resources.getString(secondDefaultValue))
    }

    private fun ifBlankUseDefault(value: String, defaultValue: String, secondDefaultValue: String): Float {
        return if (value.isBlank()) {
            defaultValue.toFloat()
        } else {
            val result = value.toFloat()
            val maxValue = max(defaultValue.toFloat(), secondDefaultValue.toFloat())
            val minValue = min(defaultValue.toFloat(), secondDefaultValue.toFloat())
            when {
                result > maxValue -> maxValue
                result < minValue -> minValue
                else -> result
            }

        }


    }

    private val handleTouch: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            val image = findViewById<ImageView>(R.id.iv_city_image) as ImageView
            val rt = image.drawable.bounds

            val drawLeft = rt.left
            val drawTop = rt.top
            val drawRight = rt.right
            val drawBottom = rt.bottom
            Log.i("XY", "y1 = $drawLeft, x1 = $drawTop, y2 = $drawBottom, x2 = $drawRight")
            val y1 = 35.82
            val x1 = 139.61
            val x2 = 139.92
            val y2 = 35.54
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val rawX = event.x.toInt() - leftCoordinate
                    val rawY = event.y.toInt() - topCoordinate
                    finalX1 = (x2 - x1) * rawX / (rightCoordinate - leftCoordinate) + x1
                    finalY1 = (y1 - y2) * ((bottomCoordinate - topCoordinate) - rawY) / (bottomCoordinate - topCoordinate) + y2
                    Log.i("Raw", "y1 = $rawY, x1 = $rawX")
                }

                MotionEvent.ACTION_UP -> {
                    val rawX = event.x.toInt() - leftCoordinate
                    val rawY = event.y.toInt() - topCoordinate
                    finalX2 = (x2 - x1) * rawX / (rightCoordinate - leftCoordinate) + x1
                    finalY2 = (y1 - y2) * ((bottomCoordinate - topCoordinate) - rawY) / (bottomCoordinate - topCoordinate) + y2
                    Log.i("Raw2", "y1 = $rawY, x1 = $rawX")
                    Log.i("Cooridnate", "y1 = $finalY1, x1 = $finalX1, y2 = $finalY2, x2 = $finalX2")
                    myAsyncTask(WeakReference(findViewById(R.id.iv_city_image)), WeakReference(findViewById(R.id.iv_city_minimap)),
                            finalY1, finalX1, finalY2, finalX2).execute()
                }

            }
            return true
        }
    }


}