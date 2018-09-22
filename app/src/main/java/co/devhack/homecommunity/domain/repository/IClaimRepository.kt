package co.devhack.homecommunity.domain.repository

import co.devhack.homecommunity.domain.model.Claim
import io.reactivex.Observable

interface IClaimRepository {

    fun add(claim: Claim): Observable<Int>

    fun getAll(): Observable<List<Claim>>

    fun getById(id: Int): Observable<Claim>

    fun delete(id: Int)

}