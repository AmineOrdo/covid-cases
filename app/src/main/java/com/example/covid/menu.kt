package com.example.covid


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.*


class Menu : AppCompatActivity(),ConnectivityReceiver.ConnectivityReceiverListener  {
    lateinit var selectedItemZone:String
    lateinit var selectedItemCat:String
     var dateCalendar : String="date"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //check internet
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        //spinner zone
        val spinner_zone = findViewById<Spinner>(R.id.spinner_zone)
        val adapter = ArrayAdapter(
                this,
                R.layout.spinner_menu,
                resources.getStringArray(R.array.list_pays)
        )
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_menu)
        spinner_zone.adapter = adapter
        spinner_zone.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
                selectedItemZone = spinner_zone.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, selectedItemZone, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        //spinner categorie
        val spinner_cat = findViewById<Spinner>(R.id.spinner_cat)
        val adapter_cat = ArrayAdapter(
                this,
                R.layout.spinner_menu,
                resources.getStringArray(R.array.list_cat)
        )
        adapter_cat.setDropDownViewResource(R.layout.drop_down_spinner_menu)
        spinner_cat.adapter = adapter_cat
        spinner_cat.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
                selectedItemCat = spinner_cat.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, selectedItemCat, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //datePicker
        pickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        text_date.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        dateCalendar = ("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth)

                    },
                    year,
                    month,
                    day
            )
            dpd.show()

        }



//bouton valider
        btn_valider.setOnClickListener{
            var CustomURl: String
            if(spinner_cat.getSelectedItem()!=null||spinner_zone.getSelectedItem()!=null) {


                //on va ici filtrer la recherche et modifier l'url comme on le souhaite
            if (dateCalendar.isEmpty()) {
                Toast.makeText(this, "please pick a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
                CustomURl = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=covid-19-pandemic-worldwide-data&q=&sort=date&facet=zone&facet=subzone&facet=category&facet=date&" +
                        "refine.category=" + spinner_cat.getSelectedItem().toString() + "&refine.zone=" + spinner_zone.getSelectedItem().toString() + "&refine.date=" + dateCalendar
                // https://public.opendatasoft.com/api/records/1.0/search/?dataset=covid-19-pandemic-worldwide-data&q=&sort=date&facet=zone&facet=subzone&facet=category&facet=date&refine.category=Confirmed&refine.zone=France

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("url", CustomURl)
                startActivity(intent)

            }else{
                Toast.makeText(this, "selectors must not be null", Toast.LENGTH_SHORT).show()
            }

        }

    }

//fonctions pour checker si il y a internet en utilisant CommunityReceiver
override fun onNetworkConnectionChanged(isConnected: Boolean) {
    showNetworkMessage(isConnected)
}

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            internet_check.visibility= VISIBLE
        } else {
            internet_check.visibility= INVISIBLE
        }
    }

}