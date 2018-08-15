package com.mobilschool.fintrack.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobilschool.fintrack.data.entity.WalletFull
import com.mobilschool.fintrack.data.interactor.WalletInteractor
import com.mobilschool.fintrack.data.repository.CurrencyRepository
import com.mobilschool.fintrack.data.repository.TransactionRepository
import com.mobilschool.fintrack.data.repository.WalletRepository
import com.mobilschool.fintrack.data.source.local.dao.WalletDao
import com.mobilschool.fintrack.data.source.local.entity.Wallet
import com.mobilschool.fintrack.data.source.local.entity.WalletType
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class WalletRepositoryTest {

    companion object {
        val walletForTesting = Wallet(1, "RUB", 10.0, "Wallet name", WalletType.CASH)
        val walletFullForTesting = WalletFull(walletForTesting, "R")
    }

    @Rule
    @JvmField
    val executorRule = InstantTaskExecutorRule()

    lateinit var walletDao: WalletDao
    lateinit var walletRepository: WalletRepository

    @Before
    fun setUp() {
        walletDao = Mockito.mock(WalletDao::class.java)

        walletRepository = WalletRepository(walletDao)
    }

    @Test
    fun getAllWallets(){
        `when`(walletDao.getAllWallets()).thenReturn(MutableLiveData<List<WalletFull>>().apply{ value = listOf() })

        LiveDataTestUtil.getValue(walletRepository.getAllWallets())

        verify(walletDao).getAllWallets()
        verifyNoMoreInteractions(walletDao)
    }

    @Test
    fun getWalletById(){
        val walletId = 5
        `when`(walletDao.getWalletById(walletId)).thenReturn(MutableLiveData<WalletFull>().apply{ value = walletFullForTesting })

        LiveDataTestUtil.getValue(walletRepository.getWalletById(walletId))

        verify(walletDao).getWalletById(walletId)
        verifyNoMoreInteractions(walletDao)
    }

    /*@Test
    fun changeBalance(){
        val walletId = 5
        val amount = 100.0

        walletRepository.changeBalance(walletId, amount)

        verify(walletDao).changeBalance(walletId, amount)

        verifyNoMoreInteractions(walletDao)
    }*/

}