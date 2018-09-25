package co.devhack.homecommunity.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "claim")
data class ClaimEntity(@PrimaryKey(autoGenerate = true)
                       @ColumnInfo(name = "id")
                       val id: Int?,
                       @ColumnInfo(name = "subject") val subject: String,
                       @ColumnInfo(name = "description") val description: String,
                       @ColumnInfo(name = "type") val type: String,
                       @ColumnInfo(name = "uriImage") val uriImage: String,
                       @ColumnInfo(name = "date") val date: String)