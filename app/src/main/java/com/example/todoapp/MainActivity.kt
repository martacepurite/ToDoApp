package com.example.todoapp



import androidx.activity.viewModels

import androidx.lifecycle.observe
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter


// mainactivity displays list selected in listsactivity
// listsactivity and mainactivity are very similar
// both use recyclerview to display contents and bottomsheetdialogfragment for editing
// and adding items
// lists and todo items are stored in a room database
// listadapters bind database items to recyclerview items
// and automatically add separate click listeners for each item
// every successful add/edit/delete action is logged to a text file with date
// and can be viewed in the app

// most database implementation classes based on:
// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0

// https://medium.com/@mandvi2346verma/bottom-sheet-dialog-fragment-kotlin-android-676c8b222715
// https://developer.android.com/guide/fragments/communicate#receive-host-activity

class MainActivity : AppCompatActivity() {

    lateinit var newItemSheetFragment: NewItemSheet
    lateinit var editItemSheetFragment: EditItemSheet
    lateinit var bottomNav : BottomNavigationView
    lateinit var titletext : TextView

    private val itemsViewModel: ItemsViewModel by viewModels {

            ItemsViewModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //file stores a log of items and lists added, edited and deleted

        val filename = "history.txt"
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val file = File(filesDir, filename)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        // sets item click function for all items

        var is_search = intent.getBooleanExtra("type",false)

        // loads selected list or results of search query

        if(is_search){
                var querytext : String = intent.getStringExtra("listname").toString()

                val adapter = ToDoItemListAdapter(itemsViewModel.searchDatabase(querytext).value){
                        itm -> todoItemClicked(itm)
                }

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                itemsViewModel.searchDatabase(querytext).observe(owner = this) { words ->
                    words?.let { adapter.submitList(it) }
                }

        }else{
                var list_to_show = intent.getIntExtra("listkey",0)

                val adapter = ToDoItemListAdapter(itemsViewModel.todosInList(list_to_show).value){
                        itm -> todoItemClicked(itm)
                }

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                itemsViewModel.todosInList(list_to_show).observe(owner = this) { words ->
                    words?.let { adapter.submitList(it) }
                }
        }

        titletext = findViewById(R.id.textViewTitle)
        val list_name = intent.getStringExtra("listname")
        titletext.text = list_name

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        if(is_search){
            fab.hide()
            // disables add item function if displaying search results (would be unclear which list to add
            // new item to)
        }
        fab.setOnClickListener {

            newItemSheetFragment = NewItemSheet()
            newItemSheetFragment.show(supportFragmentManager, "BSDialogFragment")
        }

        bottomNav = findViewById(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener{
            when (it.itemId){
                R.id.listsbtn -> {
                    startActivity(Intent(applicationContext, ListsActivity::class.java))
                    return@setOnItemSelectedListener true
                }
            }
            false
        }


        supportFragmentManager
            .setFragmentResultListener("addKey", this) { requestKey, bundle ->

                val result = bundle.getString("bundleKey")
                val current = LocalDate.now()

                val text = current.format(formatter)
                var list = intent.getIntExtra("listkey",0)
                val item = ToDoItem(0, result, text,list)
                itemsViewModel.insert(item)

                val towrite = text +":  \""+ result +"\" "+  " added to \"" + titletext.text +"\"\n"

                openFileOutput(filename,  Context.MODE_APPEND).use {
                    it.write(towrite.toByteArray())
                }


            }

        supportFragmentManager
            .setFragmentResultListener("editKey", this) { requestKey, bundle ->

                val result_text = bundle.getString("textKey")
                val result_id = bundle.getInt("idKey")
                if (result_text != null) {
                    itemsViewModel.update(result_text,result_id)

                    val current = LocalDate.now()
                    val text = current.format(formatter)

                    var towrite : String

                    if(is_search){

                        towrite = text +": item \"" + result_id +"\" changed to \""+result_text+"\"\n"
                    }else{
                        towrite = text +": item in \"" + list_name +"\" changed to \""+result_text+"\"\n"
                    }


                    openFileOutput(filename,  Context.MODE_APPEND).use {
                        it.write(towrite.toByteArray())
                    }
                }
            }

        supportFragmentManager
            .setFragmentResultListener("ignoreKey", this) { requestKey, bundle ->

                val result = bundle.getString("bundleKey")
                println(result)
            }

        supportFragmentManager
            .setFragmentResultListener("deleteKey", this) { requestKey, bundle ->

                val result = bundle.getInt("bundleKey")

                itemsViewModel.update(result)
                val itm_name = bundle.getString("nameKey")
                val current = LocalDate.now()
                val text = current.format(formatter)

                var towrite : String

                if(is_search){

                    towrite = text +":  \""+ itm_name +"\" "+" deleted\n"
                }else{
                    towrite = text +":  \""+ itm_name +"\" "+" deleted from \"" + list_name +"\"\n"
                }

                    openFileOutput(filename,  Context.MODE_APPEND).use {
                        it.write(towrite.toByteArray())
                    }

            }

    }

    private fun todoItemClicked(todo: ToDoItem) {

            editItemSheetFragment = EditItemSheet()

            val bundle = Bundle()
            bundle.putString("EditTextKey", todo.todotext)
            bundle.putString("EditDateCr", todo.date_cr)
            bundle.putInt("EditId", todo.uid)
            editItemSheetFragment.arguments = bundle
            editItemSheetFragment.show(supportFragmentManager, "BSDialogFragment")

    }

}