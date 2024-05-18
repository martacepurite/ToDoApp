package com.example.todoapp

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

class SearchItemsSheet : BottomSheetDialogFragment() {

    private lateinit var view: View
    private lateinit var query_text: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query_text = view.findViewById(R.id.searchquerytext)

        val search_button = view.findViewById<Button>(R.id.searchquerybutton)

        search_button.setOnClickListener {

            if (TextUtils.isEmpty(query_text.text)) {
                val result = "empty"
                setFragmentResult("ignoreKey", bundleOf("bundleKey" to result))

            } else {
                val result = "%" + query_text.text.toString() + "%"
                setFragmentResult("searchKey", bundleOf("bundleKey" to result))
            }
            dismiss()
        }

    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.search_items_sheet,container,false)

        return view
    }

}