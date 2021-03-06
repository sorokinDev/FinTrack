package com.mobilschool.fintrack.ui.template


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mobilschool.fintrack.R
import com.mobilschool.fintrack.data.entity.TemplateFull
import com.mobilschool.fintrack.ui.base.BaseFragment
import com.mobilschool.fintrack.ui.home.bottom_sheet.WalletsBottomSheetFragment
import com.mobilschool.fintrack.ui.template.add.TemplateAddFragment
import com.mobilschool.fintrack.util.observe
import com.mobilschool.fintrack.util.toMoneyString
import kotlinx.android.synthetic.main.fragment_templates_periodic.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.mobilschool.fintrack.data.source.local.entity.TransactionType
import java.util.concurrent.TimeUnit


class TemplatesPeriodicsFragment : BaseFragment<TemplatesPeriodicsViewModel>() {

    lateinit var vpAdapter: VPAdapter

    override fun initAdapters() {
        super.initAdapters()
        vpAdapter = VPAdapter()
        vp_templates_periodics.adapter = vpAdapter
    }

    override fun initUI() {
        super.initUI()
        tl_templates_periodics.setupWithViewPager(vp_templates_periodics)
        fab_add_template.setOnClickListener {
            val bottomSheetTemplate = TemplateAddFragment.newInstance(vp_templates_periodics.currentItem == 1)
            bottomSheetTemplate.show(fragmentManager, "template_add_fragment")
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.templates.observe(this, {
            vpAdapter.templateAdapter.setData(it)
        })

        viewModel.periodics.observe(this, {
            vpAdapter.periodicAdapter.setData(it)
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_templates_periodic
    override fun provideViewModel(): TemplatesPeriodicsViewModel = getViewModel(viewModelFactory)

    inner class VPAdapter : PagerAdapter() {

        val templateAdapter = RVTemplateAdapter()
        val periodicAdapter = RVPeriodicAdapter()

        override fun getPageTitle(position: Int): CharSequence? = when(position) {
            0 -> resources.getString(R.string.templates)
            1 -> resources.getString(R.string.periodic)
            else -> resources.getString(R.string.templates)
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            (container as ViewPager).removeView(obj as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val inflater = container.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val itemView = inflater.inflate(R.layout.rv_templates, container, false)

            val rv_templates = itemView.findViewById<RecyclerView>(R.id.rv_templates)

            rv_templates.layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL, false)


            if(position == 0){
                rv_templates.adapter = templateAdapter
            }else{
                rv_templates.adapter = periodicAdapter
            }

            container.addView(itemView)

            return itemView
        }


        override fun getCount(): Int = 2

        override fun isViewFromObject(view: View, obj: Any) = view == obj
    }

    inner class RVTemplateAdapter: RecyclerView.Adapter<RVTemplateAdapter.ViewHolder>() {
        var items = listOf<TemplateFull>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val tvCategory: TextView
            val tvSum: TextView
            val tvWallet: TextView
            val btnDelete: AppCompatImageButton

            init {
                tvCategory = itemView.findViewById(R.id.tv_template_category)
                tvSum = itemView.findViewById(R.id.tv_template_sum)
                tvWallet = itemView.findViewById(R.id.tv_template_wallet)
                btnDelete = itemView.findViewById(R.id.btn_delete_template)
                btnDelete.setOnClickListener {
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        val builder = AlertDialog.Builder(requireContext())

                        builder.setTitle(R.string.deletion)
                        builder.setMessage(R.string.are_you_sure_delete)

                        builder.setPositiveButton(R.string.yes) { dialog, which ->
                            viewModel.deleteTemplate(items[adapterPosition])
                            dialog.dismiss()
                        }

                        builder.setNegativeButton(R.string.no) { dialog, which ->

                            dialog.dismiss()
                        }

                        val alert = builder.create()
                        alert.show()
                    }
                }
            }

            fun bind(item: TemplateFull){
                tvCategory.text = item.categoryName
                tvWallet.text = item.walletName
                tvSum.text = "${item.transaction.amount.toMoneyString()} ${item.currencySymbol}"
                val textColor = ResourcesCompat.getColor(tvSum.resources, if(item.transaction.type == TransactionType.EXPENSE) { R.color.colorExpense } else { R.color.colorIncome }, null)
                tvSum.setTextColor(textColor)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_template_in_settings, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        fun setData(data: List<TemplateFull>){
            items = data
            notifyDataSetChanged()
        }
    }


    inner class RVPeriodicAdapter: RecyclerView.Adapter<RVPeriodicAdapter.ViewHolder>() {
        var items = listOf<TemplateFull>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val tvCategory: TextView
            val tvSum: TextView
            val tvWallet: TextView
            val tvPeriod: TextView
            val btnDelete: AppCompatImageButton

            init {
                tvCategory = itemView.findViewById(R.id.tv_periodic_category)
                tvSum = itemView.findViewById(R.id.tv_periodic_sum)
                tvWallet = itemView.findViewById(R.id.tv_periodic_wallet)
                tvPeriod = itemView.findViewById(R.id.tv_period)
                btnDelete = itemView.findViewById(R.id.btn_delete_periodic)
                btnDelete.setOnClickListener {
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        val builder = AlertDialog.Builder(requireContext())

                        builder.setTitle(R.string.deletion)
                        builder.setMessage(R.string.are_you_sure_delete)

                        builder.setPositiveButton(R.string.yes) { dialog, which ->
                            viewModel.deleteTemplate(items[adapterPosition])
                            dialog.dismiss()
                        }

                        builder.setNegativeButton(R.string.no) { dialog, which ->

                            dialog.dismiss()
                        }

                        val alert = builder.create()
                        alert.show()
                    }
                }
            }

            fun bind(item: TemplateFull){
                tvCategory.text = item.categoryName
                tvWallet.text = item.walletName
                tvSum.text = "${item.transaction.amount.toMoneyString()} ${item.currencySymbol}"
                val textColor = ResourcesCompat.getColor(tvSum.resources, if(item.transaction.type == TransactionType.EXPENSE) { R.color.colorExpense } else { R.color.colorIncome }, null)
                tvSum.setTextColor(textColor)
                tvPeriod.text = tvPeriod.resources.getString(R.string.template_every_x_days, (item.transaction.period / TimeUnit.DAYS.toMillis(1)).toInt())
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_periodic_in_settings, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        fun setData(data: List<TemplateFull>){
            items = data
            notifyDataSetChanged()
        }
    }


}
