package com.example.assignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private var menuList: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val course: TextView = view.findViewById(R.id.textViewItemCourse)
        val dishName: TextView = view.findViewById(R.id.textViewItemName)
        val description: TextView = view.findViewById(R.id.textViewItemDescription)
        val price: TextView = view.findViewById(R.id.textViewItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.course.text = item.course
        holder.dishName.text = item.dishName
        holder.description.text = item.description
        holder.price.text = String.format("R %.2f", item.price)
    }

    override fun getItemCount(): Int = menuList.size

    fun updateData(newList: List<MenuItem>) {
        menuList = newList
        notifyDataSetChanged()
    }
}
