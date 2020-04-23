package com.example.cardbe.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.cardbe.AboutCardBe
import com.example.cardbe.Board
import com.example.cardbe.CreateBoard
import com.example.cardbe.R
import com.example.cardbe.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    lateinit var myView : View
    lateinit var myOwnerView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_first, container, false)
        myOwnerView = inflater.inflate(R.layout.activity_home, container, false)
        if (super.isResumed())
            inflater.inflate(R.layout.activity_home, container, false).findViewById<FloatingActionButton>(R.id.fab).isVisible = true
        setBoardsClickListener(myView)
        return myView
    }

    private fun setBoardsClickListener(myView: View) {
        val listOfBoards = myView.findViewById<LinearLayout>(R.id.FirstFragmentBoardsList)
        listOfBoards.children.forEach {
            it.setOnClickListener{
                startActivity(Intent(context, Board::class.java))
            }
        }
    }
}
