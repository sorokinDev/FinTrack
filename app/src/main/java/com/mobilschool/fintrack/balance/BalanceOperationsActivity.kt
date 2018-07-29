package com.mobilschool.fintrack.CurrentBalance


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mobilschool.fintrack.AboutDialogFragment
import com.mobilschool.fintrack.BillsActivity
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.SettingsActivity
import kotlinx.android.synthetic.main.activity_balance_operations.*

class BalanceOperationsActivity : AppCompatActivity() {

    private lateinit var balancePresenter: BalancePresenter
    private lateinit var operationsPresenter: OperationsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance_operations)

        balancePresenter = BalancePresenter()
        operationsPresenter = OperationsPresenter()

        viewPager.adapter = BalanceOperationsAdapter(supportFragmentManager, resources.getStringArray(R.array.titles_tabs), balancePresenter, operationsPresenter)
        tabs.setupWithViewPager(viewPager)

        //  viewModel = ViewModelProviders.of(this).get(CurrentBalanceViewModel::class.java)
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

    private class BalanceOperationsAdapter(fm: FragmentManager?, val titlesTabs:Array<String>, val balancePresenter: BalanceContract.Presenter, val operationsPresenter: OperationsContract.Presenter) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> BalanceFragment.newInstance(balancePresenter)
                1 -> OperationsFragment.newInstance(operationsPresenter)
                else -> throw NotImplementedError()
            }
        }

        override fun getCount(): Int {
            return titlesTabs.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titlesTabs[position]
        }
    }
}
