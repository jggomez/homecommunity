package co.devhack.homecommunity.presentation.views.claims

interface IClaimsView {

    fun showLoading()

    fun hideLoading()

    fun showError(error : String)

    fun saveClaim()

    fun gotoClaimList()

    fun showIdClaim(id : Int)

    fun disableViews()

    fun enableViews()

}