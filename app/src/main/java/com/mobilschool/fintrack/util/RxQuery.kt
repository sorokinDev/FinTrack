package com.mobilschool.fintrack.util

import io.objectbox.query.Query
import io.reactivex.internal.disposables.DisposableHelper.isDisposed
import io.objectbox.reactive.DataObserver
import io.reactivex.schedulers.Schedulers.single
import io.reactivex.functions.Cancellable
import io.objectbox.reactive.DataSubscription
import io.reactivex.*
import io.reactivex.internal.subscriptions.SubscriptionHelper.isCancelled


object RxQuery {
    fun <T> flowableOneByOne(query: Query<T>): Flowable<T> {
        return flowableOneByOne(query, BackpressureStrategy.BUFFER)
    }

    /**
     * The returned Flowable emits Query results one by one. Once all results have been processed, onComplete is called.
     * Uses given BackpressureStrategy.
     */
    fun <T> flowableOneByOne(query: Query<T>, strategy: BackpressureStrategy): Flowable<T> {
        return Flowable.create({ emitter -> createListItemEmitter(query, emitter) }, strategy)
    }

    fun <T> createListItemEmitter(query: Query<T>, emitter: FlowableEmitter<T>) {
        val dataSubscription = query.subscribe().observer(DataObserver<List<T>> { data ->
            for (datum in data) {
                if (emitter.isCancelled) {
                    return@DataObserver
                } else {
                    emitter.onNext(datum)
                }
            }
            if (!emitter.isCancelled) {
                emitter.onComplete()
            }
        })
        emitter.setCancellable { dataSubscription.cancel() }
    }

    /**
     * The returned Observable emits Query results as Lists.
     * Never completes, so you will get updates when underlying data changes.
     */
    fun <T> observable(query: Query<T>): Observable<List<T>> {
        return Observable.create { emitter ->
            val dataSubscription = query.subscribe().observer { data ->
                if (!emitter.isDisposed) {
                    emitter.onNext(data)
                }
            }
            emitter.setCancellable { dataSubscription.cancel() }
        }
    }

    /**
     * The returned Single emits one Query result as a List.
     */
    fun <T> single(query: Query<T>): Single<List<T>> {
        return Single.create { emitter ->
            query.subscribe().single().observer { data ->
                if (!emitter.isDisposed) {
                    emitter.onSuccess(data)
                }
            }
            // no need to cancel, single never subscribes
        }
    }
}