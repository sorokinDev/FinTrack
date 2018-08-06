package com.mobilschool.fintrack.data.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

sealed class DBResource<T>(val data: T?)

class ResLoading<T>(data: T? = null): DBResource<T>(data)
class ResSuccess<T>(data: T): DBResource<T>(data)
class ResFailure<T>(val exception: Throwable, data: T? = null): DBResource<T>(data)

