package co.devhack.homecommunity.presentation.presenters.claims

import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.usecase.claim.GetAllClaimUseCase
import co.devhack.homecommunity.presentation.views.claims.IClaimListView
import io.reactivex.observers.DisposableObserver

class ClaimListPresenter(val view: IClaimListView,
                         val getAllClaimUseCase: GetAllClaimUseCase) {

    fun loadClaims() {
        view.showLoading()

        getAllClaimUseCase.execute(AllClaimsObserver())
    }

    fun dispose(){
        getAllClaimUseCase.dispose()
    }

    inner class AllClaimsObserver : DisposableObserver<List<Claim>>() {

        override fun onComplete() {
            view.hideLoading()
        }

        override fun onNext(lst: List<Claim>) {
            view.loadDataAdapter(lst)
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
            view.showError(e.message!!)
        }

    }
}