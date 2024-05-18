package com.example.todoapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.*

// displays lists and items added, edited, deleted from text file log
// https://www.javatpoint.com/kotlin-android-read-and-write-internal-storage

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val textView = findViewById<TextView>(R.id.textViewHistory)

        val filename = "history.txt"
        var fileInputStream: FileInputStream? = null

        try{
            fileInputStream = openFileInput(filename)

            var inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
                stringBuilder.append("\n")
            }

            textView.text = stringBuilder.toString()

        }catch(e: FileNotFoundException){

        }

    }

}