package com.example.cityapp

import android.R.attr.data
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    /*private val NAMESPACE = "http://akozlowski/soap/"
    private val METHOD_NAME1 = "getImageResponse"
    private val URL = "http://192.168.1.17:8080/getImageResponse"
    private val SOAP_ACTION = "http://akozlowski/soap/getImageResponse"
    private var webResponse = ""*/
    /*private val NAMESPACE: String? = "http://www.w3schools.com/webservices/"
    private val MAIN_REQUEST_URL = "https://www.w3schools.com/xml/tempconvert.asmx"
    private val SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius"
    private var METHOD_NAME = "FahrenheitToCelsius"
    private var webResponse = ""*/

    private val SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit"
    private val METHOD_NAME = "CelsiusToFahrenheit"
    private val NAMESPACE = "http://www.w3schools.com/webservices/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn_get_image)
        button.setOnClickListener {
            val y1 = findViewById<EditText>(R.id.y1).text.toString().toInt()
            val x1 = findViewById<EditText>(R.id.x1).text.toString().toInt()
            val y2 = findViewById<EditText>(R.id.y2).text.toString().toInt()
            val x2 = findViewById<EditText>(R.id.x2).text.toString().toInt()
            val myRequest = myAsyncTask(MainActivity(), x1, x2, y1, y2)

            myRequest.execute()
            /*  val bis = ByteArrayInputStream(execute)
              val bImage2: BufferedImage = ImageIO.read(bis)
              ImageIO.write(bImage2, "jpg", File("output.jpg"))*/
        }

    }


    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
         val button = findViewById<Button>(R.id.btn_get_image)
         val cityImage = findViewById<ImageView>(R.id.iv_city_image)
         val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
         StrictMode.setThreadPolicy(policy)

         button.setOnClickListener {
             *//*val y1 = findViewById<EditText>(R.id.y1).text.toString().toInt()
            val x1 = findViewById<EditText>(R.id.x1).text.toString().toInt()
            val y2 = findViewById<EditText>(R.id.y2).text.toString().toInt()
            val x2 = findViewById<EditText>(R.id.x2).text.toString().toInt()
            val url = "/getImage?y1=$y1&x1=$x1&y2=$y2&x2=$x2"*//*
            launch(Dispatchers.Main) {
                //        setContentView(R.layout.fragment_item_detail);
//        textView1 = (TextView) findViewById(R.id.item_detail);
                *//*val title = intent.extras!!.getString("ClientCode")

                println("Data is $title")
                val getReport = GetReport()
                getReport.startWebAccess(title)*//*
                try {
                    val request = SoapObject(NAMESPACE, METHOD_NAME)

                    val envelope = SoapSerializationEnvelope(
                        SoapEnvelope.VER11
                    )
                    envelope.dotNet = true
                    envelope.setOutputSoapObject(request)

                    val androidHttpTransport = HttpTransportSE(
                        MAIN_REQUEST_URL
                    )
                    androidHttpTransport.debug = true
                    androidHttpTransport.call(SOAP_ACTION, envelope)
                    val objectResult = envelope.bodyIn as SoapObject
                    webResponse = objectResult.toString()
                }catch (xe: XmlPullParserException){
                    xe.printStackTrace()

                }catch (e: Exception) {
                    e.printStackTrace()
                }


               *//* RetrofitImage.getBitmapFrom(url){
                    cityImage.load(it)
                }*//*

            }
        }
    }*/
}