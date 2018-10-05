package co.devhack.homecommunity.presentation.views.claims

import co.devhack.homecommunity.domain.model.Claim

interface IClaimListView {

    fun showLoading()

    fun hideLoading()

    fun addNewClaim()

    fun loadDataAdapter(lstClaims: List<Claim>)

    fun showError(error: String)
}