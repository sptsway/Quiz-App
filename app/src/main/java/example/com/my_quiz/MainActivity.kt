package example.com.my_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.renderscript.ScriptGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
//import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import example.com.my_quiz.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawer : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        @Suppress("UNUSED VARIABLE")
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        drawer= binding.drawerLayout
        val navcontrol= this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navcontrol,drawer)
        NavigationUI.setupWithNavController(binding.navView,navcontrol)

        // prevent nav gesture if not on start destination
        navcontrol.addOnDestinationChangedListener { nc, nd, args ->
            if (nd.id ==nc.graph.startDestination){
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
            else{
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawer)
    }
}

