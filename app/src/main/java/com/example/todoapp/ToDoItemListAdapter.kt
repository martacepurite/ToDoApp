package com.example.todoapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// binds data to recyclerview items, adds click listeners to each item
// https://www.myandroidsolutions.com/2018/08/17/kotlin-recyclerview/
// https://antonioleiva.com/recyclerview-listener/

class ToDoItemListAdapter(
    private val todoList : List<ToDoItem>?,
    private val clickListener: (ToDoItem) -> Unit):
    ListAdapter<ToDoItem, ToDoItemListAdapter.ToDoItemViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ToDoItemViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ToDoItemViewHolder(itemView: View, onClick: (ToDoItem) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val todoItemView: TextView = itemView.findViewById(R.id.textView)
        private val viewButton : ImageButton = itemView.findViewById(R.id.imageButton)
        private var currentTodo: ToDoItem? = null

        fun bind(todo :ToDoItem) {

            todoItemView.text = todo.todotext
            currentTodo = todo
        }

        init {

            viewButton.setOnClickListener {
                currentTodo?.let { onClick(it) }
            }

        }

    }

    class ItemComparator : DiffUtil.ItemCallback<ToDoItem>() {
        override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
            return oldItem.todotext == newItem.todotext
        }

    }

}