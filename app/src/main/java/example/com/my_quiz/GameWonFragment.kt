package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import example.com.my_quiz.R
import example.com.my_quiz.databinding.FragmentGameWonBinding

//import com.example.android.navigation.databinding.FragmentGameOverBinding
//import example.com.my_quiz.databinding.FragmentGameOverBinding

class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view : View->
            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment3)
        }
        return binding.root
    }
}
