package com.asap.frolics.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asap.frolics.R
import com.asap.frolics.model.AlphaChar

class AlphaAdapter(var context: Context, var arrayList: ArrayList<AlphaChar>,val onItemClick: onItemClickListener)  : RecyclerView.Adapter<AlphaAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_list_item , parent , false)
        return ItemHolder(itemHolder,onItemClick)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var alphaChar:AlphaChar = arrayList.get(position)
        holder.icons.setImageResource(alphaChar.iconsChar!!)
        holder.alphas.text= alphaChar.alphaChar
    }

    class ItemHolder(itemView:View,onItemClick: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init{
            itemView.setOnClickListener{
                onItemClick.onClick(adapterPosition)
            }
            itemView.setOnLongClickListener{
                onItemClick.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        var icons = itemView.findViewById<ImageView>(R.id.icons_image)
        var alphas = itemView.findViewById<TextView>(R.id.alpha_text_view)
    }
    interface onItemClickListener {
        fun onClick(position:Int)
        fun onLongClick(position: Int)
    }
}