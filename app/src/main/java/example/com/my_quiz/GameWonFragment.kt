package example.com.my_quiz

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
            val startTime : Long = System.currentTimeMillis()
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment3(startTime))
        }

        val args= arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        if (args != null) {
            Toast.makeText(context,"All Correct: ${args.numCorrect}/${args.numQuestions}",Toast.LENGTH_SHORT).show()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_win,menu)
    }

    private fun getShareIntent() : Intent{
        val args= arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        val send = Intent(Intent.ACTION_SEND)
        val str: String= "I scored ${args?.numCorrect?:0}/${args?.numQuestions?:3} in My Quiz App developed by tt89"
        send.setType("text/plain").putExtra(Intent.EXTRA_TEXT,str)

        return send
    }

    private fun sharesuccess(){
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share-> sharesuccess()
        }
        return super.onOptionsItemSelected(item)
    }



}
