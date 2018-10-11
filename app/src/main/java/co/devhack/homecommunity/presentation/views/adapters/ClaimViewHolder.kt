package co.devhack.homecommunity.presentation.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.devhack.homecommunity.R
import co.devhack.homecommunity.util.uri
import co.devhack.homecommunity.domain.model.Claim
import com.squareup.picasso.Picasso

class ClaimViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    private var txtSubject: TextView = itemView!!.findViewById(R.id.txtSubject)
    private var txtDescription: TextView = itemView!!.findViewById(R.id.txtDescription)
    private var txtType: TextView = itemView!!.findViewById(R.id.txtType)
    private var txtDate: TextView = itemView!!.findViewById(R.id.txtDate)
    private var img: ImageView = itemView!!.findViewById(R.id.imgClaim)

    fun render(claim: Claim) {
        txtSubject.text = claim.subject
        txtDescription.text = claim.description
        txtType.text = claim.type
        txtDate.text = claim.date

        Picasso.get().load(claim.uriImage.uri()).fit().centerCrop().into(img)
    }
}