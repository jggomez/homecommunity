package co.devhack.homecommunity.domain.usecase

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

abstract class UseCase<T>(private val executorThread: Scheduler,
                          private val uiThread: Scheduler) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun execute(observer: DisposableObserver<T>) {

        val observable = createObservable()

        observable.subscribeOn(executorThread)
                .observeOn(uiThread)

        observable.subscribeWith(observer)

        this.compositeDisposable.add(observer)

    }

    abstract fun createObservable(): Observable<T>

    fun dispose() = compositeDisposable.dispose()

}