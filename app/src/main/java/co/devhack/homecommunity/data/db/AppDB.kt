package co.devhack.homecommunity.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import co.devhack.homecommunity.data.entities.claim.ClaimEntity

@Database(entities = [(ClaimEntity::class)],
        version = 1)
abstract class AppDB : RoomDatabase() {

    companion object {
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB? {
            if (instance == null) {
                instance = Room.databaseBuilder(context,
                        AppDB::class.java,
                        "homecommunity-db")
                        .allowMainThreadQueries()
                        .build()
            }

            return instance
        }
    }

    abstract fun ClaimDAO(): IClaimDAO

}