package co.devhack.homecommunity.data.entities.mapper

import co.devhack.homecommunity.data.entities.ClaimEntity
import co.devhack.homecommunity.domain.model.Claim

class ClaimEntityMapper {

    fun map(claimEntity: ClaimEntity): Claim {
        return Claim(
                claimEntity.id,
                claimEntity.subject,
                claimEntity.description,
                claimEntity.type,
                claimEntity.uriImage,
                claimEntity.date
        )
    }

    fun map(lstClaimEntity: List<ClaimEntity>): List<Claim> {

        val lstClaim = ArrayList<Claim>()

        lstClaimEntity.forEach {
            lstClaim.add(map(it))
        }

        return lstClaim
    }

    fun reverseMap(claim: Claim): ClaimEntity {
        return ClaimEntity(
                claim.id,
                claim.subject,
                claim.description,
                claim.type,
                claim.uriImage,
                claim.date
        )
    }

    fun reverseMap(lstClaim: List<Claim>): List<ClaimEntity> {

        val lstClaimEntity = ArrayList<ClaimEntity>()

        lstClaim.forEach {
            lstClaimEntity.add(reverseMap(it))
        }

        return lstClaimEntity
    }


}