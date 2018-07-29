package com.mobilschool.fintrack.CurrentBalance

class BalancePresenter : BalanceContract.Presenter {
    override fun getBalance(to:String): Double {
        return 50.0
    }

    override fun getBalanceByCategories(): Map<String, Double> {
        return mapOf("Продукты" to 10.0, "Зарплата" to 10.0, "ЖКХ" to 30.0)
    }
}