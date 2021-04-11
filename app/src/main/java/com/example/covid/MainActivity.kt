package com.example.covid

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
//on récupère l'url de l'activité précédente
        val url:String = intent.getStringExtra("url").toString()


        val ListView:ListView=findViewById(R.id.listview)

//on appelle l'async task et on récupère la liste remplie retournée
        val listRemplie= MyAsyncTask().execute(url).get()

        //on applique l'adapter avec la liste remplie
        val adapterReturn = ListAdapter(this, listRemplie)
        ListView.adapter = adapterReturn



        }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    }







