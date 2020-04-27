package example.com.my_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.renderscript.ScriptGroup
import androidx.databinding.DataBindingUtil
//import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import example.com.my_quiz.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        @Suppress("UNUSED VARIABLE")
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navcontrol= this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navcontrol)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }
}

