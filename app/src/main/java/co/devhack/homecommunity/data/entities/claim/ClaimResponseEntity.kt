package co.devhack.homecommunity.data.entities.claim

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ClaimResponseEntity(
        @SerializedName("status")
        @Expose
        val status: Int,
        @SerializedName("response")
        @Expose
        val count: Int)