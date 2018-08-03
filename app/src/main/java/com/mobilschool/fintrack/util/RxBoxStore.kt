package com.mobilschool.fintrack.util

import io.reactivex.functions.Cancellable
import io.reactivex.internal.disposables.DisposableHelper.isDisposed
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.objectbox.BoxStore
import io.reactivex.Observable


object RxBoxStore {
    fun <T> observable(boxStore: BoxStore): Observable<Class<*>> {
        return Observable.create { emitter ->
            val dataSubscription = boxStore.subscribe().observer { data ->
                if (!emitter.isDisposed) {
                    emitter.onNext(data)
                }
            }
            emitter.setCancellable { dataSubscription.cancel() }
        }
    }
}