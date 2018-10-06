package co.devhack.homecommunity.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import co.devhack.homecommunity.data.db.AppDB
import co.devhack.homecommunity.data.entities.mapper.ClaimEntityMapper
import co.devhack.homecommunity.data.repositories.ClaimCloudSource
import co.devhack.homecommunity.data.repositories.ClaimDBSource
import co.devhack.homecommunity.data.repositories.ClaimRepository
import co.devhack.homecommunity.domain.usecase.claim.SyncClaimsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SyncClaimService(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        try {

            val syncClaim =
                    SyncClaimsUseCase(Schedulers.io(),
                            AndroidSchedulers.mainThread(),
                            ClaimRepository(
                                    ClaimDBSource(
                                            AppDB.getInstance(applicationContext)?.ClaimDAO()!!
                                    ),
                                    ClaimCloudSource(),
                                    ClaimEntityMapper()
                            )
                    )

            syncClaim.execute(SyncClaimObserver())

        } catch (e: Exception) {
            return Result.FAILURE
        }

        return Result.SUCCESS
    }

    inner class SyncClaimObserver : DisposableObserver<Boolean>() {

        override fun onComplete() {
            Log.i("SynClaimService", "Termino bien")
        }

        override fun onNext(resp: Boolean) {
            Log.i("SynClaimService", resp.toString())
        }

        override fun onError(e: Throwable) {
            Log.e("SynClaimService", e.message)
        }


    }
}