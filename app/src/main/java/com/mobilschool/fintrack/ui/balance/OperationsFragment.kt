package com.mobilschool.fintrack.ui.balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.Operation

class OperationsFragment : Fragment(), OperationsContract.View {

    override lateinit var presenter: OperationsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_operations, container, false)

        view.findViewById<RecyclerView>(R.id.list_operations).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = OperationsAdapter(presenter.getOperations())
        }

        return view
    }

    private class OperationsAdapter(private val operationList: List<Operation>) : RecyclerView.Adapter<OperationsAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_operation, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return operationList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.amount.text = operationList[position].amount
            holder.category.text = operationList[position].category
            holder.date.text = operationList[position].date
            holder.currencyName.text = operationList[position].currencyName
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
        fun newInstance(): OperationsFragment {
                val fragment = OperationsFragment()
            return fragment
        }
    }
}




