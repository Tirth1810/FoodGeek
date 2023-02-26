package com.example.foofgeek

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class AddHowtoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_howto, container, false)
        var imm: InputMethodManager? = null
        var eidtHowto: EditText? = null
        retainInstance = true
        //hidding keybord
        imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        eidtHowto = view.findViewById(R.id.txt_ADD_Howto)
        val root = view.findViewById<ConstraintLayout>(R.id.root_Add_howto)
        root.setOnClickListener { imm!!.hideSoftInputFromWindow(eidtHowto.windowToken, 0) }
        // Inflate the layout for this fragment
        return view
    }
}