package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// activity to display all todo lists
// listsactivity and mainactivity are very similar

// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0
// https://medium.com/@mandvi2346verma/bottom-sheet-dialog-fragment-kotlin-android-676c8b222715
// https://developer.android.com/guide/fragments/communicate#receive-host-activity

class ListsActivity : AppCompatActivity() {

    lateinit var editListSheet: EditListSheet
    lateinit var newListSheet: NewListSheet
    lateinit var searchItemsSheet: SearchItemsSheet
    private val listsViewModel: ListsViewModel by viewModels {
        ListViewModelFactory((application as TodoApplication).repository2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lists_activity)

        val filename = "history.txt"
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview2)
        val adapter = ListListAdapter(listsViewModel.allLists.value,{listItemClicked(it)},{listItemLongClicked(it)})

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        listsViewModel.allLists.observe(owner = this) {
                words ->
            words?.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab2)

        fab.setOnClickListener{
            newListSheet = NewListSheet()
            newListSheet.show(supportFragmentManager,"BSDialogFragment")
        }

        val histbtn = findViewById<Button>(R.id.histbutton)

        histbtn.setOnClickListener{
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        val searchbtn = findViewById<Button>(R.id.buttonsearch)

        searchbtn.setOnClickListener {
            searchItemsSheet = SearchItemsSheet()
            searchItemsSheet.show(supportFragmentManager,"BSDialogFragment")
        }

        supportFragmentManager
            .setFragmentResultListener("searchKey", this) { requestKey, bundle ->

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("type",true)
                intent.putExtra("listname", bundle.getString("bundleKey"))

                startActivity(intent)

            }


        supportFragmentManager
            .setFragmentResultListener("addKey", this) { requestKey, bundle ->

                val result = bundle.getString("bundleKey")
                println(result)

                val list = ListItem(0,result)
                listsViewModel.insert(list)

                val current = LocalDate.now()
                val text = current.format(formatter)

                val towrite = text +":  list \""+ result + "\" created\n"

                openFileOutput(filename,  Context.MODE_APPEND).use {
                    it.write(towrite.toByteArray())
                }
            }

        supportFragmentManager
            .setFragmentResultListener("editKey", this) { requestKey, bundle ->

                val result_text = bundle.getString("textKey")
                val result_id = bundle.getInt("idKey")
                if (result_text != null) {
                    listsViewModel.update(result_text,result_id)

                    val current = LocalDate.now()
                    val text = current.format(formatter)

                    val towrite = text +":  list "+ result_id + " changed to \""+result_text+"\"\n"

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
                val list_name = bundle.getString("text_key")

                val itemsViewModel: ItemsViewModel by viewModels {

                    ItemsViewModelFactory((application as TodoApplication).repository)
                }

                itemsViewModel.delete(result)
                listsViewModel.update(result)

                val current = LocalDate.now()
                val text = current.format(formatter)

                val towrite = text +":  list \""+ list_name + "\" deleted\n"

                openFileOutput(filename,  Context.MODE_APPEND).use {
                    it.write(towrite.toByteArray())
                }
            }

    }

    private fun listItemClicked(list: ListItem) {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("type",false)
        intent.putExtra("listkey",list.uid)
        intent.putExtra("listname", list.listname)
        startActivity(intent)
    }

    private fun listItemLongClicked(list: ListItem){
        editListSheet = EditListSheet()

        val bundle = Bundle()
        bundle.putString("EditTextKey", list.listname)
        bundle.putInt("EditId", list.uid)
        editListSheet.arguments = bundle
        editListSheet.show(supportFragmentManager, "BSDialogFragment")
    }
}