package com.example.todoapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// binds data to recyclerview items, adds click listeners to each item
// https://www.myandroidsolutions.com/2018/08/17/kotlin-recyclerview/
// https://antonioleiva.com/recyclerview-listener/

class ListListAdapter(
    private val todoList : List<ListItem>?,
    private val clickListener: (ListItem) -> Unit,
    private val longClickListener: (ListItem) -> Unit):
    ListAdapter<ListItem, ListListAdapter.ListItemViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_lists, parent, false)
        return ListItemViewHolder(view, clickListener, longClickListener)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ListItemViewHolder(itemView: View, onClick: (ListItem) -> Unit, onLongClick: (ListItem) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val listItemView: TextView = itemView.findViewById(R.id.textViewList)

        private var currentList: ListItem? = null

        fun bind(list :ListItem) {

            listItemView.text = list.listname
            currentList = list
        }

        init {

            itemView.setOnClickListener{
                currentList?.let { onClick(it)}
            }

            itemView.setOnLongClickListener{
                currentList?.let { onLongClick(it)}
               true
            }

        }

    }

    class ItemComparator : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.listname == newItem.listname
        }

    }

}