package com.mobilschool.fincalc


class Operations {

    companion object {
        fun sumAllRecords(records: List<Record>, nameCurrency: String): Double {

            val groupingTypeOperations = records.groupBy { it.typeOperation }

            val incomeAmount = groupingTypeOperations[TypeOperation.INCOME]
                    ?.map { it.currency.convertTo(nameCurrency).amount }
                    ?.sum() ?: 0.0

            val outcomeAmount = groupingTypeOperations[TypeOperation.OUTCOME]
                    ?.map { it.currency.convertTo(nameCurrency).amount }
                    ?.sum() ?: 0.0

            return incomeAmount - outcomeAmount

        }

        fun sumAllOperations(records: List<Record>, nameCurrency: String): Double =
                records.sumByDouble { it.currency.convertTo(nameCurrency).amount }


    }

}