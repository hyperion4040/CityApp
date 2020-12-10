package com.example.cityapp

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val NAMESPACE = "http://akozlowski/soap/"
    private val METHOD_NAME1 = "getImageResponse"
    private val URL = "http://192.168.1.17:8080/getImageResponse"
    private val SOAP_ACTION = "http://akozlowski/soap/getImageResponse"
    private var webResponse = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn_get_image)
        val cityImage = findViewById<ImageView>(R.id.iv_city_image)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        button.setOnClickListener {
            /*val y1 = findViewById<EditText>(R.id.y1).text.toString().toInt()
            val x1 = findViewById<EditText>(R.id.x1).text.toString().toInt()
            val y2 = findViewById<EditText>(R.id.y2).text.toString().toInt()
            val x2 = findViewById<EditText>(R.id.x2).text.toString().toInt()
            val url = "/getImage?y1=$y1&x1=$x1&y2=$y2&x2=$x2"*/
            launch(Dispatchers.Main) {
                //        setContentView(R.layout.fragment_item_detail);
//        textView1 = (TextView) findViewById(R.id.item_detail);
                /*val title = intent.extras!!.getString("ClientCode")

                println("Data is $title")
                val getReport = GetReport()
                getReport.startWebAccess(title)*/
                try {
                    val request = SoapObject(NAMESPACE, METHOD_NAME1)

                    val envelope = SoapSerializationEnvelope(
                            SoapEnvelope.VER11
                    )
                    envelope.dotNet = true
                    envelope.setOutputSoapObject(request)

                    val androidHttpTransport = HttpTransportSE(
                            URL
                    )
                    androidHttpTransport.debug = true
                    androidHttpTransport.call(SOAP_ACTION, envelope)
                    val objectResult = envelope.bodyIn as SoapObject
                    webResponse = objectResult.toString()
                }catch(xe: XmlPullParserException){
                    xe.printStackTrace()

                }catch (e: Exception) {
                    e.printStackTrace()
                }


               /* RetrofitImage.getBitmapFrom(url){
                    cityImage.load(it)
                }*/

            }
        }
    }
}