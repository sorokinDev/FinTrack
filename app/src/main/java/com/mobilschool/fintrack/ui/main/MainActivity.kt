package com.mobilschool.fintrack.ui.main


import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.di.factory.ViewModelFactory
import com.mobilschool.fintrack.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    override fun provideViewModel(): MainViewModel = getViewModel(viewModelFactory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean =
            findNavController(R.id.fragment_main_nav_host).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

}
