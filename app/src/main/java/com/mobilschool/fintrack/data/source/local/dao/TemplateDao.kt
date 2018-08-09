package com.mobilschool.fintrack.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mobilschool.fintrack.data.entity.TemplateFull
import com.mobilschool.fintrack.data.source.local.entity.Template
import java.util.*

@Dao
abstract class TemplateDao : BaseDao<Template>{

    @Query(
"""SELECT templates.*, wallets.name AS wallet_name, wallets.type as wallet_type, currencies.symbol AS currency_symbol, categories.name AS category_name, categories.img_res AS category_img_res
        FROM templates
            INNER JOIN wallets ON templates.wallet_id = wallets.id
            INNER JOIN currencies ON templates.currency_id = currencies.id
            INNER JOIN categories ON templates.category_id = categories.id
        WHERE (templates.period > 0)
        ORDER BY templates.date ASC""")
    abstract fun getAllPeriodicTransactions(): LiveData<List<TemplateFull>>

    @Query(
"""SELECT templates.*, wallets.name AS wallet_name, wallets.type as wallet_type, currencies.symbol AS currency_symbol, categories.name AS category_name, categories.img_res AS category_img_res
        FROM templates
            INNER JOIN wallets ON templates.wallet_id = wallets.id
            INNER JOIN currencies ON templates.currency_id = currencies.id
            INNER JOIN categories ON templates.category_id = categories.id
        WHERE (templates.period > 0) AND (date + period) > :dateNow
        ORDER BY templates.date ASC""")
    abstract fun getPendingPeriodicTransactions(dateNow: Long): LiveData<List<TemplateFull>>

    @Query(
"""SELECT templates.*, wallets.name AS wallet_name, wallets.type as wallet_type, currencies.symbol AS currency_symbol, categories.name AS category_name, categories.img_res AS category_img_res
        FROM templates
            INNER JOIN wallets ON templates.wallet_id = wallets.id
            INNER JOIN currencies ON templates.currency_id = currencies.id
            INNER JOIN categories ON templates.category_id = categories.id
        WHERE (templates.period = 0)
        ORDER BY templates.id DESC""")
    abstract fun getAllTemplates(): LiveData<List<TemplateFull>>

    @Query("UPDATE templates SET date = :newDate WHERE id = :id")
    abstract fun updatePeriodicTransactionLastExecution(id: Int, newDate: Long)


}