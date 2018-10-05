package co.devhack.homecommunity.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import co.devhack.homecommunity.data.entities.ClaimEntity

@Dao
interface IClaimDAO {

    @Insert
    fun insert(claimEntity: ClaimEntity): Long

    @Delete
    fun delete(claimEntity: ClaimEntity)

    @Query("Select * From claim order by date desc")
    fun getAll(): List<ClaimEntity>

    @Query("Select * From claim where id = :id")
    fun getById(id: Int): ClaimEntity

}