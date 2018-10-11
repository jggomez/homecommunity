package co.devhack.homecommunity.data.net.services

import co.devhack.homecommunity.data.entities.claim.ClaimResponseEntity
import co.devhack.homecommunity.data.entities.claim.LstClaimEntity
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface IClaimService {

    @POST("claims")
    fun add(@Body lstClaimEntity: LstClaimEntity): Observable<ClaimResponseEntity>

}