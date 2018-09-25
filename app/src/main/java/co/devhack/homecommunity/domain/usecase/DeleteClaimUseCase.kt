package co.devhack.homecommunity.domain.usecase

import co.devhack.homecommunity.domain.repository.IClaimRepository
import io.reactivex.Observable
import io.reactivex.Scheduler

class DeleteClaimUseCase(executorThread: Scheduler,
                         uiThread: Scheduler,
                         private val claimRepository: IClaimRepository)
    : UseCase<Boolean>(executorThread, uiThread) {


    override fun createObservable(): Observable<Boolean> {
        return claimRepository.delete(this.id)
    }

    var id: Int = 0

}