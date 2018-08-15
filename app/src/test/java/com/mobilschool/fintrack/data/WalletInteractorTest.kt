package com.mobilschool.fintrack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.repository.CurrencyRepository
import com.mobilschool.fintrack.data.repository.TransactionRepository
import com.mobilschool.fintrack.data.repository.WalletRepository
import com.mobilschool.fintrack.data.source.local.entity.*
import com.mobilschool.fintrack.data.source.local.entity.Currency
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import java.util.*

@RunWith(JUnit4::class)
class WalletInteractorTest {

    companion object {
        val walletForTesting = Wallet(1, "RUB", 10.0, "Wallet name", WalletType.CASH)
        val walletFullForTesting = WalletFull(walletForTesting, "R")
    }

    private lateinit var walletRepository: WalletRepository
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var currencyRepository: CurrencyRepository

    private lateinit var walletInteractor: WalletInteractor

    @Rule
    @JvmField
    val executorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        walletRepository = mock(WalletRepository::class.java)
        currencyRepository = mock(CurrencyRepository::class.java)
        transactionRepository = mock(TransactionRepository::class.java)

        walletInteractor = WalletInteractor(currencyRepository, walletRepository, transactionRepository)
    }

    fun insertsTransaction(transStub: Transaction) {
        walletInteractor.insertTransaction(transStub)

        verify(transactionRepository).insertOrUpdateTransaction(transStub)
        verify(walletRepository).changeBalance(transStub.walletId, transStub.amount * (if (transStub.type == TransactionType.INCOME) 1 else (-1)))

        verifyNoMoreInteractions(currencyRepository, transactionRepository)
    }

    @Test
    fun insertsIncomeTransacrtion() {
        insertsTransaction(Transaction(0, "RUB", 1, Date().time, 50.0, 1, TransactionType.INCOME))
    }

    @Test
    fun insertsExpenseTransacrtion() {
        insertsTransaction(Transaction(0, "RUB", 1, Date().time, 50.0, 1, TransactionType.EXPENSE))
    }

    @Test
    fun getsWalletById() {
        val liveDataStub = MutableLiveData<WalletFull>()
        liveDataStub.value = walletFullForTesting
        `when`(walletRepository.getWalletById(1)).thenReturn(liveDataStub)

        val res = LiveDataTestUtil.getValue(walletInteractor.getWalletById(1))

        verify(walletRepository).getWalletById(1)
        verifyNoMoreInteractions(walletRepository)
    }

    @Test
    fun exchangesMoney_correctly() {
        val liveDataStub = MutableLiveData<List<ExchangeRate>>()
        val ratesStub = listOf(ExchangeRate("RUB_USD", 60.0, Date().time))
        liveDataStub.value = ratesStub
        `when`(currencyRepository.getExchangeRates("RUB", listOf("USD"))).thenReturn(liveDataStub)

        val res = LiveDataTestUtil.getValue(walletInteractor.exchangeMoney(10.0, "RUB", listOf(Currency("USD", "R", true))))

        verify(currencyRepository).getExchangeRates("RUB", listOf("USD"))
        verifyNoMoreInteractions(currencyRepository)
        assertEquals(res[0].second, 600.0)
    }

    @Test
    fun getWalletBalanceInFavoriteCurrencies_correctly() {
        val liveDataFavCurr = MutableLiveData<List<Currency>>()
        val favCurrStub = listOf(Currency("RUB", "R", true), Currency("USD", "$", true), Currency("EUR", "E", true))
        liveDataFavCurr.value = favCurrStub
        `when`(currencyRepository.getFavoriteCurrencies()).thenReturn(liveDataFavCurr)

        val liveDataExchange = MutableLiveData<List<ExchangeRate>>()
        val ratesStub = listOf(ExchangeRate("RUB_USD", 60.0, Date().time), ExchangeRate("RUB_EUR", 80.0, Date().time))
        liveDataExchange.value = ratesStub
        `when`(currencyRepository.getExchangeRates("RUB", listOf("USD", "EUR"))).thenReturn(liveDataExchange)

        val res = LiveDataTestUtil.getValue(walletInteractor.getWalletBalanceInFavoriteCurrencies(walletForTesting))

        verify(currencyRepository).getFavoriteCurrencies()
        verify(currencyRepository).getExchangeRates("RUB", listOf("USD", "EUR"))
        verifyNoMoreInteractions(currencyRepository)
        assertEquals(res[0].second, 600.0)
        assertEquals(res[1].second, 800.0)
    }


}