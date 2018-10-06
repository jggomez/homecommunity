package co.devhack.homecommunity.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import co.devhack.homecommunity.data.entities.claim.ClaimEntity

@Dao
interface IClaimDAO {

    @Insert
    fun insert(claimEntity: ClaimEntity): Long

    @Query("Delete From Claim")
    fun deleteAll()

    @Query("Select * From claim order by date desc")
    fun getAll(): List<ClaimEntity>

    @Query("Select * From claim where id = :id")
    fun getById(id: Int): ClaimEntity

}