package co.devhack.homecommunity.domain.usecase

import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.repository.IClaimRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class CreateClaimUseCase {

    private val claimRepository: IClaimRepository
    private val executorThread: Scheduler
    private val uiThread: Scheduler
    private val compositeDisposable: CompositeDisposable

    lateinit var claim: Claim

    constructor(claimRepository: IClaimRepository,
                executorThread: Scheduler,
                uiThread: Scheduler
    ) {
        this.claimRepository = claimRepository
        this.executorThread = executorThread
        this.uiThread = uiThread
        this.compositeDisposable = CompositeDisposable()
    }

    fun execute(observer: DisposableObserver<Int>) {

        val observable = claimRepository.add(this.claim)

        observable.subscribeOn(executorThread)
                .observeOn(uiThread)

        observable.subscribeWith(observer)

        this.compositeDisposable.add(observer)

    }

    fun dispose(){
        compositeDisposable.dispose()
    }

}