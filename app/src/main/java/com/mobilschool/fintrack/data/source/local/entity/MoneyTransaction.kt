package com.mobilschool.fintrack.data.source.local.entity

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import io.objectbox.relation.ToOne

enum class TransactionType{
    INCOME, EXPENSE
}

class TransactionTypeConverter : PropertyConverter<TransactionType, Int>{
    companion object {
        private val convertMap = arrayOf(
                Pair(TransactionType.INCOME, 0),
                Pair(TransactionType.EXPENSE, 1)
        )
    }


    override fun convertToDatabaseValue(entityProperty: TransactionType?): Int {
        return convertMap.first { it.first == entityProperty }.second
    }

    override fun convertToEntityProperty(databaseValue: Int?): TransactionType {
        return convertMap.first { it.second == databaseValue }.first
    }

}

@Entity
class TransactionCategory(
        @Id var id: Long = 0,
        var name: String,
        @Convert(converter = TransactionTypeConverter::class, dbType = Int::class) val type: TransactionType
)


@Entity
class MoneyTransaction (
        @Id var id: Long = 0,
        val amount: Double,
        @Convert(converter = TransactionTypeConverter::class, dbType = Int::class) val type: TransactionType
){
    lateinit var currency: ToOne<MoneyCurrency>
    lateinit var wallet: ToOne<Wallet>
    lateinit var category: ToOne<TransactionCategory>
}

