package example.com.my_quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import example.com.my_quiz.R
import example.com.my_quiz.databinding.FragmentGameOverBinding

//import com.example.android.navigation.databinding.FragmentGameOverBinding
//import example.com.my_quiz.databinding.FragmentGameOverBinding

class GameOverFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameOverBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_over, container, false)
        binding.tryAgainButton.setOnClickListener { view : View->
            view.findNavController().navigate(R.id.action_gameOverFragment2_to_gameFragment)
        }

        val args= arguments?.let { GameOverFragmentArgs.fromBundle(it) }
        if (args != null) {
            Toast.makeText(context,"No. of Correct: ${args.numCorrect}/${args.numQuestions}",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}
