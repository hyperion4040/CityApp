package com.example.cityapp

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import coil.api.load
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpResponseException
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException


class myAsyncTask(val activity: MainActivity, val x1: Int, val x2: Int, val y1: Int, val y2: Int) : AsyncTask<Any?, Void?, Any?>() {



//    private val SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit"
    /*private val SOAP_ACTION = "https://www.w3schools.com/xml/CelsiusToFahrenheit"
    private val METHOD_NAME = "CelsiusToFahrenheit"
    private val NAMESPACE = "http://www.w3schools.com/webservices/"*/
    /*private val SOAP_ACTION = "http://spring.io/guides/gs-producing-web-service/getCountryRequest"
    private val METHOD_NAME = "getCountryRequest"
    private val NAMESPACE = "http://spring.io/guides/gs-producing-web-service"*/
private val SOAP_ACTION = "http://spring.io/guides/gs-producing-web-service/getImageRequest"
    private val METHOD_NAME = "getImageRequest"
    private val NAMESPACE = "http://akozlowski/soap"
    override fun doInBackground(vararg params: Any?): Any? {
        val URL = "http://192.168.1.26:8080/ws"
        val MAIN_REQUEST_URL = "https://www.w3schools.com/xml/tempconvert.asmx"

        //for linear parameter
        val request = SoapObject(NAMESPACE, METHOD_NAME)
//        request.addProperty("Celsius", "48") // adding method property here serially
        /*val s: String = "Spain"
        request.addProperty("name", s)*/
       /* val GameId = PropertyInfo()

        GameId.setName("name")
        GameId.value = "Spain"
        request.addProperty(GameId);*/
        /*val property2 = PropertyInfo()
        property2.name = "name"
        property2.setName("name")
        property2.setNamespace(NAMESPACE)
        property2.setType(PropertyInfo.OBJECT_TYPE)
        property2.value = nameS
        request.addProperty(property2)*/
        request.addProperty(createProperty("y1", y1))
        request.addProperty(createProperty("x1", x1))
        request.addProperty(createProperty("y2", y2))
        request.addProperty(createProperty("x2", x2))



        Log.i("request", "request:" + request);
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.implicitTypes = true
        envelope.setOutputSoapObject(request)
        val httpTransport = HttpTransportSE(URL)
        httpTransport.debug = true
        try {
            httpTransport.debug = true;
            val requestDump = httpTransport.requestDump
            httpTransport.call(SOAP_ACTION, envelope)
        } catch (e: HttpResponseException) {
            // TODO Auto-generated catch block
            Log.e("HTTPLOG", e.message.toString())
            e.printStackTrace()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            Log.e("IOLOG", e.message.toString())
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            // TODO Auto-generated catch block
            Log.e("XMLLOG", e.message.toString())
            e.printStackTrace()
        } //send request
        var result: ByteArray? = null
        try {
            result = envelope.response as ByteArray
            Log.i("RESPONSE", result.toString()) // see output in the console
        } catch (e: SoapFault) {
            // TODO Auto-generated catch block
            Log.e("SOAPLOG", e.message.toString())
            e.printStackTrace()
        }
        return result
    }

    override fun onPostExecute(result: Any?) {
        val imageData: ByteArray = result as ByteArray
        val bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        val cityImage = activity.findViewById<ImageView>(R.id.iv_city_image)
        cityImage.load(bmp)
    }

    fun createProperty(propertyName: String, propertyValue: Int): PropertyInfo {
        val property2 = PropertyInfo()
        property2.name = propertyName
        property2.setName(propertyName)
        property2.setNamespace(NAMESPACE)
        property2.setType(PropertyInfo.OBJECT_TYPE)
        property2.value = propertyValue

        return property2;
    }
}