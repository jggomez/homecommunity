package co.devhack.homecommunity.domain.model

data class Claim(val id: Int?,
                 val subject: String,
                 val description: String,
                 val type: String,
                 val uriImage: String
)