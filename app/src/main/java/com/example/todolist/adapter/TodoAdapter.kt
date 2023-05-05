package com.example.todolist.adapter

import android.content.Context
import android.content.LocusId
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.dto.TodoDTO
import org.w3c.dom.Text

class TodoAdapter(val context: Context) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var list = mutableListOf<TodoDTO>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.timeStamp.text = list[position].time
        holder.chkBox.isChecked = list[position].isChecked
        if(list[position].isChecked){
            holder.title.paintFlags = holder.title.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            holder.title.paintFlags = holder.title.paintFlags or STRIKE_THRU_TEXT_FLAG.inv()
        }
        holder.chkBox.setOnClickListener{
            itemCheckBoxClickListener.onClick(it,position,list[position].id)


        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position,list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.tvTodoItem)
        var timeStamp = itemView.findViewById<TextView>(R.id.tvTimeStamp)
        var chkBox = itemView.findViewById<CheckBox>(R.id.cbCheck)



    }

    fun update(newList : MutableList<TodoDTO>){
        this.list = newList
        notifyDataSetChanged()
    }
    interface ItemCheckBoxClickListener{
        fun onClick(view:View,position: Int,itemId: Long)
    }
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener){
        this.itemCheckBoxClickListener = itemCheckBoxClickListener

    }

    interface ItemClickListener{
        fun onClick(view:View , position: Int,itemId: Long)
    }
    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}