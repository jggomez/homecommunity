package co.devhack.homecommunity.domain.usecase.claim

import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.repository.IClaimRepository
import co.devhack.homecommunity.domain.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetAllClaimUseCase(executorThread: Scheduler,
                         uiThread: Scheduler,
                         private val claimRepository: IClaimRepository)
    : UseCase<List<Claim>>(executorThread, uiThread) {

    override fun createObservable(): Observable<List<Claim>> {
        return claimRepository.getAll()
    }

}