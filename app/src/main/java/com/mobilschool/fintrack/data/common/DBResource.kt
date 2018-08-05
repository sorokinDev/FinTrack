package com.mobilschool.fintrack.data.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

sealed class DBResource<T>

class ResLoading<T>(val data: T? = null): DBResource<T>()
class ResSuccess<T>(val data: T): DBResource<T>()
class ResFailure<T>(val exception: Throwable, val data: T? = null): DBResource<T>()

