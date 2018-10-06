package co.devhack.homecommunity.data.entities.claim

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "claim")
data class ClaimEntity(@PrimaryKey(autoGenerate = true)
                       @ColumnInfo(name = "id")
                       val id: Int?,
                       @SerializedName("subject") @Expose @ColumnInfo(name = "subject") val subject: String,
                       @SerializedName("description") @Expose @ColumnInfo(name = "description") val description: String,
                       @SerializedName("type") @Expose @ColumnInfo(name = "type") val type: String,
                       @SerializedName("urlImagen") @Expose @ColumnInfo(name = "uriImage") val uriImage: String,
                       @SerializedName("date") @Expose @ColumnInfo(name = "date") val date: String)