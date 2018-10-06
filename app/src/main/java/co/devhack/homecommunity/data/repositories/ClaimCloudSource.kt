package co.devhack.homecommunity.data.repositories

import co.devhack.homecommunity.data.entities.claim.ClaimResponseEntity
import co.devhack.homecommunity.data.entities.claim.LstClaimEntity
import co.devhack.homecommunity.data.net.ServicesFabric
import io.reactivex.Observable

class ClaimCloudSource {

    fun add(lstClaimEntity: LstClaimEntity): Observable<ClaimResponseEntity>? {
        return ServicesFabric.getClaimService()?.add(lstClaimEntity)
    }
}