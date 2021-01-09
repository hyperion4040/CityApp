package com.example.cityapp

import android.os.AsyncTask
import android.util.Log
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpResponseException
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException


class myAsyncTask : AsyncTask<Void?, Void?, Void?>() {

//    private val SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit"
    /*private val SOAP_ACTION = "https://www.w3schools.com/xml/CelsiusToFahrenheit"
    private val METHOD_NAME = "CelsiusToFahrenheit"
    private val NAMESPACE = "http://www.w3schools.com/webservices/"*/
    private val SOAP_ACTION = ""
    private val METHOD_NAME = "getCountryRequest"
    private val NAMESPACE = "http://spring.io/guides/gs-producing-web-service"
    override fun doInBackground(vararg params: Void?): Void? {
        val URL = "http://192.168.1.26:8080/ws"
        val MAIN_REQUEST_URL = "https://www.w3schools.com/xml/tempconvert.asmx"

        //for linear parameter
        val request = SoapObject(NAMESPACE, METHOD_NAME)
//        request.addProperty("Celsius", "48") // adding method property here serially
        request.addProperty("name", "Spain")
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.implicitTypes = true
        envelope.setOutputSoapObject(request)
        envelope.dotNet = false
        val httpTransport = HttpTransportSE(URL)
        httpTransport.debug = true
        try {
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
        var result: Any? = null
        try {
            result = envelope.response as Any
            Log.i("RESPONSE", result.toString()) // see output in the console
        } catch (e: SoapFault) {
            // TODO Auto-generated catch block
            Log.e("SOAPLOG", e.message.toString())
            e.printStackTrace()
        }
        return null
    }

}