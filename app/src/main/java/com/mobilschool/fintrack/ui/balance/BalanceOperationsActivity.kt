package com.mobilschool.fintrack.ui.balance


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.AboutDialogFragment
import com.mobilschool.fintrack.ui.SettingsActivity
import com.mobilschool.fintrack.ui.bills.BillsActivity
import kotlinx.android.synthetic.main.activity_balance_operations.*

class BalanceOperationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance_operations)
        initViewPager()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.about -> {
                val aboutDialog = AboutDialogFragment()
                aboutDialog.show(supportFragmentManager, "about_dialog")
                true
            }
            R.id.cash_accounts -> {
                val intent = Intent(this, BillsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun initViewPager() {

        val adapter = BalanceOperationsAdapter(supportFragmentManager)

        val balanceFragment = BalanceFragment.newInstance()
        val operationsFragment = OperationsFragment.newInstance()

        adapter.addFragment(balanceFragment, resources.getStringArray(R.array.titles_tabs)[0])
        adapter.addFragment(operationsFragment, resources.getStringArray(R.array.titles_tabs)[1])

        val balancePresenter = BalancePresenter(balanceFragment)
        val operationsPresenter = OperationsPresenter(operationsFragment)
        // dialogPresenter = AddNewOperationPresenter(AddNewOperationDialogFragment())

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private class BalanceOperationsAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

        private val fragmentList: MutableList<Fragment> = mutableListOf()
        private val titles: MutableList<String> = mutableListOf()

        override fun getItem(position: Int): Fragment = fragmentList[position]

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence? = titles[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titles.add(title)
        }
    }
}
