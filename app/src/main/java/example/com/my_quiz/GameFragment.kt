package example.com.my_quiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import example.com.my_quiz.database.databaseHandler
import example.com.my_quiz.database.myScore
import example.com.my_quiz.databinding.FragmentGameBinding

//import com.example.android.navigation.databinding.FragmentGameBinding
//import example.com.my_quiz.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (or better yet, not define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "India is a federal union comprising twenty-nine states and how many union territories?",
                    answers = listOf("7", "6", "8", "9")),
            Question(text = " Which of the following states is not located in the North?",
                    answers = listOf("Jharkhand", "Jammu and Kashmir", "Himachal Pradesh", "Haryana")),
            Question(text = "The World Largest desert is ?",
                    answers = listOf("Sahara", "Kalahari", "Thar", "Sonoran")),
            Question(text = "How many sides are there in a triangle?",
                    answers = listOf("3", "4", "6", "5")),
            Question(text = "How many consonants are there in the English alphabet?",
                    answers = listOf("21", "20", "5", "26")),
            Question(text = "Build system for Android?",
                    answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
            Question(text = "How many days are there in the month of February in a leap year?",
                    answers = listOf("29", "31", "28", "30")),
            Question(text = "Which is the largest animal in the world?",
                    answers = listOf("Blue whale", "Lion", "Elephant", "Giraffe")),
            Question(text = "Which festival is called the festival of light?",
                    answers = listOf("Diwali", "Holi", "Raksha Bandhan", "Durga Puja")),
            Question(text = "What is the top color in a rainbow?",
                    answers = listOf("Red", "Violet", "Blue", "Sky"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size+1) / 2, 5)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)
        val args= arguments?.let { GameFragmentArgs.fromBundle(it) }

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        val endTime : Long = System.currentTimeMillis()
                        val duration : Double = (endTime.toDouble()- (args?.startTime ?: 0).toDouble())/1000
                        //val duration : Long = endTime- (args?.startTime ?:0)
                        //Toast.makeText(context,"Duaration : $duration ",Toast.LENGTH_SHORT).show()

                        val curScore= myScore(questionIndex,duration)
                        val db= databaseHandler(context!!)
                        db.insertScore(curScore)

                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                    val endTime : Long = System.currentTimeMillis()
                    val duration : Double = (endTime.toDouble()- (args?.startTime ?: 0).toDouble())/1000
                    //Toast.makeText(context,"Duaration : $duration ",Toast.LENGTH_SHORT).show()
                    //val duration : Long = endTime- (args?.startTime ?:0)

                    val curScore= myScore(questionIndex,duration)
                    val db= context?.let { databaseHandler(it) }
                    if (db != null) {
                        db.insertScore(curScore)
                    }

                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(numQuestions,questionIndex))
                }
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    @SuppressLint("StringFormatInvalid")
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
