package co.devhack.homecommunity.data.repositories

import co.devhack.homecommunity.data.db.IClaimDAO
import co.devhack.homecommunity.data.entities.claim.ClaimEntity
import io.reactivex.Observable

class ClaimDBSource(private val claimDAO: IClaimDAO) {


    fun add(claim: ClaimEntity): Observable<Int> {
        return Observable.create {
            try {
                val id = claimDAO.insert(claim).toInt()
                it.onNext(id)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    fun getAll(): Observable<List<ClaimEntity>> {
        return Observable.create {
            try {
                it.onNext(claimDAO.getAll())
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    fun getById(id: Int): Observable<ClaimEntity> {
        return Observable.create {
            try {
                it.onNext(claimDAO.getById(id))
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

    fun deleteAll(): Observable<Boolean> {
        return Observable.create {
            try {
                claimDAO.deleteAll()
                it.onNext(true)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

}