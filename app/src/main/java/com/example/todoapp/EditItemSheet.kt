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
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditItemSheet : BottomSheetDialogFragment() {

    private lateinit var view: View
    private lateinit var new_todo_text: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_todo_text = view.findViewById(R.id.edittodotxt)

        val subm_button = view.findViewById<Button>(R.id.saveButton2)
        val del_button = view.findViewById<Button>(R.id.deleteButton)
        val bundle = requireArguments()

        val txt = bundle.getString("EditTextKey")
        val datecreated = bundle.getString("EditDateCr")
        val date = view.findViewById<TextView>(R.id.textViewCreated)

        date.text = datecreated

        new_todo_text.setText(txt)

        subm_button.setOnClickListener {
            if (TextUtils.isEmpty(new_todo_text.text)) {
                val result = "empty"
                setFragmentResult("ignoreKey", bundleOf("bundleKey" to result))

            } else {

                val newtext = new_todo_text.text.toString()
                val todoid = bundle.getInt("EditId")
                setFragmentResult("editKey", bundleOf(
                    "textKey" to newtext,
                    "idKey" to todoid

                ))
            }
            dismiss()
        }

        del_button.setOnClickListener {
            val del_id = bundle.getInt("EditId")
            val del_name = txt
            setFragmentResult("deleteKey", bundleOf(
                "bundleKey" to del_id,
                "nameKey" to del_name))
            dismiss()
        }

    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.edit_item_sheet,container,false)

        return view
    }




}