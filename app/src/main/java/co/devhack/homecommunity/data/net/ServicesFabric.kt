package co.devhack.homecommunity.data.net

import co.devhack.homecommunity.data.net.services.IClaimService

class ServicesFabric {

    companion object {
        private var claimService: IClaimService? = null

        fun getClaimService(): IClaimService {
            if (claimService == null) {
                claimService = RetrofitHelper
                        .getRetrofit().create(IClaimService::class.java)
            }

            return claimService!!
        }
    }
}