package co.devhack.homecommunity.presentation.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.devhack.homecommunity.R
import co.devhack.homecommunity.domain.model.Claim

class ClaimAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lstClaim = ArrayList<Claim>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_claim, parent, false)
        return ClaimViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstClaim.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val claimViewHolder = holder as ClaimViewHolder
        claimViewHolder.render(lstClaim[position])
    }

    fun addAllItems(collection: List<Claim>) {
        lstClaim.clear()
        lstClaim.addAll(collection)
    }
}