package com.example.covid

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class ListAdapter(val context: Context, var list:ArrayList<covid_builder>):BaseAdapter() {

    override fun getCount(): Int {
    return list.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View=LayoutInflater.from(context).inflate(R.layout.conteneur_covid,parent,false)

        val date=view.findViewById<TextView>(R.id.date)
        val zone=view.findViewById<TextView>(R.id.zone)
        val category=view.findViewById<TextView>(R.id.category)
        val count=view.findViewById<TextView>(R.id.count)
        val location=view.findViewById<TextView>(R.id.location)
        val subzone=view.findViewById<TextView>(R.id.subzone)

        date.text=list[position].date
        zone.text=list[position].zone

        category.text=list[position].category
        count.text= list[position].count.toString()
        location.text=list[position].location
        subzone.text=list[position].subzone
return view

    }


}