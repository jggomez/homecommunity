package co.devhack.homecommunity.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import co.devhack.homecommunity.data.entities.ClaimEntity

@Dao
interface IClaimDAO {

    @Insert
    fun insert(claimEntity: ClaimEntity)

    @Delete
    fun delete(id: Int)

    @Query("Select * From claim order by id asc")
    fun getAll()

    @Query("Select * From claim where id = :id")
    fun getById(id: Int)

}