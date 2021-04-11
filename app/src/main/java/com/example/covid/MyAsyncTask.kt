package com.example.covid

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class MyAsyncTask : AsyncTask<Any, Void, ArrayList<covid_builder>>() {
lateinit var pDialog:ProgressDialog
    override fun onPreExecute() {
        super.onPreExecute()
       // pDialog=ProgressDialog(this@MainActivity)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun doInBackground(vararg params: Any?): ArrayList<covid_builder> {
        var jsonStr: String? =null

        try {
            //on récupère tout d'abord les données jsnon dans un Stringbuilder
            val URL = URL(params[0] as String)
            val urlConnection: HttpsURLConnection = URL.openConnection() as HttpsURLConnection
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                // chargement du flux
                val isr = InputStreamReader(urlConnection.getInputStream())
                val input = BufferedReader(isr)
                val stringBuilder = StringBuilder()
                var temp: String?
                while (input.readLine().also { temp = it } != null) {
                    stringBuilder.append(temp)
                }
                jsonStr = stringBuilder.toString()

                input.close()
            }
        }
        finally {

        }


        return jsonResult(jsonStr)

    }

    override fun onPostExecute(jsonString: ArrayList<covid_builder>) {
        super.onPostExecute(jsonString)
    }

    private fun jsonResult(jsonString: String?): ArrayList<covid_builder> {
    //Ensuite, on se déplace dans les branches du json. La liste des données se trouve dans records
        val list = ArrayList<covid_builder>()
        val jsonRoot = JSONObject(jsonString)
        val jsonArray = jsonRoot.getJSONArray("records")

        var i = 0
        //Sur chaque elements, la liste des attributs se trouve dans fields
        while (i < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val fields = jsonObject.getJSONObject("fields")
            var subzone:String
            try {
                subzone= fields.getString("subzone")
            } catch (exception: JSONException) {
                subzone="Métropole"
            }
            //On crée donc une liste sur laquelle on va stocker les attributs json de chaque element de records
            list.add(
                    //on utilise donc le construcuteur covid builder
                    covid_builder(
                            fields.getString("date"),
                            fields.getString("zone"),
                            fields.getString("category"),
                            fields.getInt("count").toString() + "total cases",
                            fields.getString("location"),
                            subzone
                    )
            )
            i++
        }
//on retourne la liste avec tous les données json
    return list

    }




}