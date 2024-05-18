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
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditListSheet : BottomSheetDialogFragment() {

    private lateinit var view: View
    private lateinit var new_list_text: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_list_text = view.findViewById(R.id.editlisttxt)

        val subm_button = view.findViewById<Button>(R.id.saveButton3)
        val del_button = view.findViewById<Button>(R.id.deleteListButton)
        val bundle = requireArguments()

        val txt = bundle.getString("EditTextKey")

        new_list_text.setText(txt)

        subm_button.setOnClickListener {
            if (TextUtils.isEmpty(new_list_text.text)) {
                val result = "empty"
                setFragmentResult("ignoreKey", bundleOf("bundleKey" to result))

            } else {

                val newtext = new_list_text.text.toString()
                val listid = bundle.getInt("EditId")
                setFragmentResult("editKey", bundleOf(
                    "textKey" to newtext,
                    "idKey" to listid

                ))
            }
            dismiss()
        }

        del_button.setOnClickListener {
            val del_id = bundle.getInt("EditId")

            setFragmentResult("deleteKey", bundleOf(
                "bundleKey" to del_id,
                "text_key" to txt))
            dismiss()
        }

    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.edit_list_sheet,container,false)

        return view
    }




}