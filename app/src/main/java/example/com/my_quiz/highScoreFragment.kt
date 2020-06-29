package example.com.my_quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import example.com.my_quiz.database.databaseHandler
import example.com.my_quiz.database.myScore
import example.com.my_quiz.databinding.FragmentHighScoreBinding
import kotlinx.android.synthetic.main.fragment_high_score.*
import kotlinx.android.synthetic.main.fragment_high_score.view.*

/**
 * A simple [Fragment] subclass.
 */
class highScoreFragment : Fragment() {

    private lateinit var db : databaseHandler
    private lateinit var binding: FragmentHighScoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_high_score,container,false)
        // Inflate the layout for this fragment

        db= context?.let { databaseHandler(it) }!!
        getScore()


        binding.clear.setOnClickListener {
            db.deleteData()
            getScore()
        }

        return binding.root
    }

    fun getScore() {
        val data = db.readData()
        //val list : MutableList<String> = ArrayList()
        //val arrayAdapter: ArrayAdapter<*>
        binding.scoreList.text=""
        for (i in 0 until data.size) {
            val s : String= "SCORE : ${data.get(i).score}   - - - - - - -  DUARTION: ${data.get(i).duration}\n"
            binding.scoreList.append(s)
            //list.add( "SCORE : ${data.get(i).score} --- DUARTION: ${data.get(i).duration}" )
            //println("SCORE : ${data.get(i).score} --- DUARTION: ${data.get(i).duration}")
        }

//        arrayAdapter = context?.let {
//            ArrayAdapter(
//                it,
//                android.R.layout.simple_list_item_1, list)
//        }!!
//
//        binding.scoreList.adapter= arrayAdapter
    }

}
