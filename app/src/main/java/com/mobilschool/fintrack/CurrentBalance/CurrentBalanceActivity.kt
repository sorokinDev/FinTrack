package com.mobilschool.fintrack.CurrentBalance


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.mobilschool.fintrack.AboutDialogFragment
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.SettingsActivity
import kotlinx.android.synthetic.main.activity_current_balance.*

class CurrentBalanceActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrentBalanceViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_balance)

        viewModel = ViewModelProviders.of(this).get(CurrentBalanceViewModel::class.java)

        currentBalanceRub.text = String.format("%.2f",viewModel.getBalance("RUB"))
        currentBalanceUsd.text =  String.format("%.2f",viewModel.getBalance("USD"))

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.about ->{
                val aboutDialog = AboutDialogFragment()
                aboutDialog.show(supportFragmentManager, "about_dialog")
                true
            }
            else-> super.onOptionsItemSelected(item)
        }

    }
}
