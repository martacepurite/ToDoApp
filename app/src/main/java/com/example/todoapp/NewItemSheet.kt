package com.example.todoapp

import android.app.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener

class NewItemSheet : BottomSheetDialogFragment() {

    private lateinit var view: View
    private lateinit var new_todo_text: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_todo_text = view.findViewById(R.id.newtodotxt)

        val subm_button = view.findViewById<Button>(R.id.saveButton)

        subm_button.setOnClickListener {

            if (TextUtils.isEmpty(new_todo_text.text)) {
                val result = "empty"
                setFragmentResult("ignoreKey", bundleOf("bundleKey" to result))

            } else {
                val result = new_todo_text.text.toString()
                setFragmentResult("addKey", bundleOf("bundleKey" to result))
            }
            dismiss()
        }

    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.new_item_sheet,container,false)

        return view
    }




}