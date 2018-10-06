package co.devhack.homecommunity.domain.usecase.claim

import co.devhack.homecommunity.domain.repository.IClaimRepository
import co.devhack.homecommunity.domain.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class SyncClaimsUseCase(executorThread: Scheduler,
                        uiThread: Scheduler,
                        private val claimRepository: IClaimRepository) :
        UseCase<Boolean>(executorThread, uiThread) {


    override fun createObservable(): Observable<Boolean> {
        return claimRepository.getAll()
                .filter { it.isNotEmpty() }
                .flatMap { claimRepository.sync(it) }
                .flatMap {

                    if (it) {
                        claimRepository.deleteAll()
                    } else
                        throw Exception("Error Sync from Server")
                }

    }


}