package com.example.cardbe.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.cardbe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

//LISTAR DINAMICAMENTE OS TEAMS


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_second, container, false)
        inflater.inflate(R.layout.activity_home, container, false).findViewById<FloatingActionButton>(R.id.fab).isVisible = true
        return myView
    }
}
