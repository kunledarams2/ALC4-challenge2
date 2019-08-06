package com.e.alcchallenge2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.alcchallenge2.Model.ItemModel
import com.e.alcchallenge2.Model.ProductList
import com.e.alcchallenge2.R
import com.squareup.picasso.Picasso
import java.util.zip.Inflater

class ItemAdapter(var items:ArrayList<ProductList>, val context: Context): RecyclerView.Adapter<ItemAdapter.mHolderView>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.mHolderView {

        val layoutInflater= LayoutInflater.from(context)
        val view:View= layoutInflater.inflate(R.layout.userviewcontainer,parent,false)
        return mHolderView(view)
    }

    override fun getItemCount(): Int {
      return  items.size
    }

    override fun onBindViewHolder(holder: ItemAdapter.mHolderView, position: Int) {

        holder.prodname.text= items[position].productname
        holder.proddescrip.text= items[position].productdscrip
        holder.prodprice.text= items[position].productprice

        Glide.with(context)
                .load(items[position].imageuri)
                .into(holder.pimage)

    }

    class mHolderView(itemView: View) : RecyclerView.ViewHolder(itemView){

        var prodname= itemView.findViewById<TextView>(R.id.productName)
        var prodprice= itemView.findViewById<TextView>(R.id.productPrice)
        var proddescrip= itemView.findViewById<TextView>(R.id.productDescrip)
        var pimage=itemView.findViewById<ImageView>(R.id.imageView)


    }
}