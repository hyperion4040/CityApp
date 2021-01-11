package com.example.cityapp

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault
import org.ksoap2.serialization.MarshalFloat
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpResponseException
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.lang.ref.WeakReference


class myAsyncTask(val imageView: WeakReference<ImageView>, val y1: Number, val x1: Number, val y2: Number, val x2: Number) : AsyncTask<Any?, Void?, Any?>() {

    private val SOAP_ACTION = "http://spring.io/guides/gs-producing-web-service/getImageRequest"
    private val METHOD_NAME = "getImageRequest"
    private val NAMESPACE = "http://akozlowski/soap"
    private val URL = "http://192.168.1.26:8080/ws"
    override fun doInBackground(vararg params: Any?): Any? {

        val request = SoapObject(NAMESPACE, METHOD_NAME)


        request.addProperty("y1", y1)
        request.addProperty("x1", x1)
        request.addProperty("y2", y2)
        request.addProperty("x2",  x2)

        Log.i("request", "request:" + request);
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.implicitTypes = true
        envelope.setOutputSoapObject(request)
        MarshalFloat().register(envelope)
        val httpTransport = HttpTransportSE(URL)
        httpTransport.debug = true
        try {
            httpTransport.debug = true;
            val requestDump = httpTransport.requestDump
            httpTransport.call(SOAP_ACTION, envelope)
        } catch (e: HttpResponseException) {
            Log.e("HTTPLOG", e.message.toString())
            e.printStackTrace()
        } catch (e: IOException) {
            Log.e("IOLOG", e.message.toString())
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            Log.e("XMLLOG", e.message.toString())
            e.printStackTrace()
        } //send request
        var result: SoapObject = SoapObject()
        try {


            result = envelope.bodyIn as SoapObject
            Log.i("RESPONSE", result.toString()) // see output in the console
        } catch (e: SoapFault) {
            Log.e("SOAPLOG", e.message.toString())
            e.printStackTrace()
        }
        val encodedImage = result.getProperty("image").toString()
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return decodedString
    }

    override fun onPostExecute(result: Any?) {

        val imageData: ByteArray = result as ByteArray
        val bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        imageView.get()?.setImageBitmap(bmp)
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