package co.devhack.homecommunity.presentation.presenters.claims

import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.usecase.claim.CreateClaimUseCase
import co.devhack.homecommunity.presentation.views.claims.IClaimsView
import io.reactivex.observers.DisposableObserver

class ClaimPresenter(val view: IClaimsView,
                     val createClaimUseCase: CreateClaimUseCase) {

    fun saveClaim(claim: Claim) {
        view.showLoading()
        view.disableViews()
        createClaimUseCase.claim = claim
        createClaimUseCase.execute(CreateClaimObserver())
    }

    fun dispose(){
        createClaimUseCase.dispose()
    }

    inner class CreateClaimObserver : DisposableObserver<Int>() {

        override fun onComplete() {
            view.hideLoading()
            view.gotoClaimList()
            view.enableViews()
        }

        override fun onNext(id: Int) {
            view.showIdClaim(id)
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
            view.enableViews()
            view.showError(e.message!!)
        }

    }


}