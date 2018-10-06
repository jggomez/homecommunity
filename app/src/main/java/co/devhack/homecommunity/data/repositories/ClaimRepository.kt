package co.devhack.homecommunity.data.repositories

import co.devhack.homecommunity.data.entities.claim.LstClaimEntity
import co.devhack.homecommunity.data.entities.mapper.ClaimEntityMapper
import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.repository.IClaimRepository
import io.reactivex.Observable

class ClaimRepository(private val claimDBSource: ClaimDBSource,
                      private val claimCloudSource: ClaimCloudSource,
                      private val mapper: ClaimEntityMapper)
    : IClaimRepository {

    override fun add(claim: Claim) =
            claimDBSource.add(mapper.reverseMap(claim))

    override fun getAll(): Observable<List<Claim>> {
        return claimDBSource.getAll()
                .map {
                    mapper.map(it)
                }
    }

    override fun getById(id: Int) = claimDBSource.getById(id)
            .map {
                mapper.map(it)
            }


    override fun deleteAll(): Observable<Boolean> {
        return claimDBSource.deleteAll()
    }

    override fun sync(lstClaims: List<Claim>): Observable<Boolean> {
        val lstClaimEntity = LstClaimEntity(mapper.reverseMap(lstClaims))
        return claimCloudSource.add(lstClaimEntity)!!
                .flatMap {
                    val resp = it
                    Observable.create<Boolean> {

                        if (resp.status == 200) {
                            it.onNext(true)
                        } else {
                            it.onNext(false)
                        }

                        it.onComplete()
                    }
                }
    }

}