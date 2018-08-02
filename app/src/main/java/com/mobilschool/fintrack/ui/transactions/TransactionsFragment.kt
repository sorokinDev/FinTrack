package com.mobilschool.fintrack.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.model.Transaction
import com.mobilschool.fintrack.ui.base.BaseFragment
import javax.inject.Inject

class TransactionsFragment : BaseFragment(), TransactionsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: TransactionsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_operations, container, false)
        view.findViewById<RecyclerView>(R.id.list_operations).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = OperationsAdapter(presenter.getOperations())
        }
        return view
    }

    private class OperationsAdapter(private val transactionList: List<Transaction>) : RecyclerView.Adapter<OperationsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_operation, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return transactionList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.amount.text = transactionList[position].amount
            holder.category.text = transactionList[position].category
            holder.date.text = transactionList[position].date
            holder.currencyName.text = transactionList[position].currencyName
            //holder.typeOperation.drawable
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val amount = itemView.findViewById<TextView>(R.id.amount)
            val date = itemView.findViewById<TextView>(R.id.date)
            val category = itemView.findViewById<TextView>(R.id.category)
            val currencyName = itemView.findViewById<TextView>(R.id.currencyName)
            val typeOperation = itemView.findViewById<ImageView>(R.id.typeOperation)
        }
    }

    companion object {
        fun newInstance(): TransactionsFragment {
            val fragment = TransactionsFragment()
            return fragment
        }
    }
}




