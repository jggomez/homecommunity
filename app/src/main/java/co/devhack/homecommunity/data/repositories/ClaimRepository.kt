package co.devhack.homecommunity.data.repositories

import co.devhack.homecommunity.data.entities.mapper.ClaimEntityMapper
import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.repository.IClaimRepository
import io.reactivex.Observable

class ClaimRepository(private val claimDBSource: ClaimDBSource,
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


    override fun delete(id: Int): Observable<Boolean> {
        return claimDBSource.getById(id)
                .flatMap { claimDBSource.delete(it) }
    }

}