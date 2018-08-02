package com.mobilschool.fintrack.ui.main


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mobilschool.fintrack.FinTrackerApplication
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.ui.AboutDialogFragment
import com.mobilschool.fintrack.ui.balance.BalanceFragment
import com.mobilschool.fintrack.ui.base.BaseActivity
import com.mobilschool.fintrack.ui.settings.SettingsActivity
import com.mobilschool.fintrack.ui.transactions.TransactionsFragment
import kotlinx.android.synthetic.main.activity_balance_operations.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {
    override fun showMsg(par: String) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance_operations)
        init()
    }

    private fun init() {
        val adapter = BalanceOperationsAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
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

                val adapter = MaterialSimpleListAdapter { dialog, index, item ->
                    changeAccount(item.content.toString())
                    dialog.hide()
                }


                FinTrackerApplication.mockAccounts.forEach {
                    adapter.add(MaterialSimpleListItem.Builder(this)
                            .content(it.uniqueName)
                            .icon(R.drawable.ic_bills)
                            .backgroundColor(Color.WHITE)
                            .build())
                }


                MaterialDialog.Builder(this)
                        .title(R.string.title_bills_activity)
                        .adapter(adapter, null)
                        .show()

                /*  val intent = Intent(this, BillsActivity::class.java)
                  startActivity(intent)*/
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun changeAccount(name: String) {
        FinTrackerApplication.CURRENT_ACCOUNT = name
        viewPager.adapter?.notifyDataSetChanged()
    }

    private inner class BalanceOperationsAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }

        override fun getItem(position: Int): Fragment {

            return when (position) {
                0 -> {
                    val balanceFragment = BalanceFragment.newInstance()
                    balanceFragment
                }
                1 -> {
                    val operationsFragment = TransactionsFragment.newInstance()
                    operationsFragment
                }
                else -> {
                    throw NotImplementedError()
                }
            }

        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? =
                resources.getStringArray(R.array.titles_tabs)[position]

    }
}
