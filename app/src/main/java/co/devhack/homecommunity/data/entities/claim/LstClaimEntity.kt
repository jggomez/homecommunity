package co.devhack.homecommunity.data.entities.claim

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LstClaimEntity(@SerializedName("claims")
                          @Expose
                          val lstClaimsEntity: List<ClaimEntity>)