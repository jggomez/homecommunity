package co.devhack.homecommunity.presentation.views.claims

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import co.devhack.homecommunity.R
import co.devhack.homecommunity.data.db.AppDB
import co.devhack.homecommunity.data.entities.mapper.ClaimEntityMapper
import co.devhack.homecommunity.data.repositories.ClaimDBSource
import co.devhack.homecommunity.data.repositories.ClaimRepository
import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.usecase.claim.GetAllClaimUseCase
import co.devhack.homecommunity.presentation.presenters.claims.ClaimListPresenter
import co.devhack.homecommunity.presentation.views.adapters.ClaimAdapter
import com.afollestad.materialdialogs.MaterialDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_claim_list.*

class ClaimListActivity : AppCompatActivity(), IClaimListView {

    private lateinit var materialDialog: MaterialDialog
    private lateinit var claimAdapter: ClaimAdapter
    private lateinit var presenter: ClaimListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim_list)

        btnAddClaim.setOnClickListener { addNewClaim() }

        initAdapter()
        initRecyclerView()

        presenter = ClaimListPresenter(this,
                GetAllClaimUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        ClaimRepository(
                                ClaimDBSource(
                                        AppDB.getInstance(this)?.ClaimDAO()!!
                                ),
                                ClaimEntityMapper()
                        )
                )
        )

    }

    override fun onResume() {
        super.onResume()
        presenter.loadClaims()
    }

    private fun initRecyclerView() {
        rvClaims.layoutManager = LinearLayoutManager(this)
        rvClaims.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))
        rvClaims.setHasFixedSize(true)
        rvClaims.adapter = claimAdapter
    }

    private fun initAdapter() {
        claimAdapter = ClaimAdapter()
    }

    override fun showLoading() {
        materialDialog = MaterialDialog.Builder(this)
                .title(R.string.loading)
                .content(R.string.wait_please)
                .progress(true, 0)
                .show()
    }

    override fun hideLoading() {
        materialDialog.dismiss()
    }

    override fun addNewClaim() {
        val intent = Intent(this, ClaimsActivity::class.java)
        startActivity(intent)
    }

    override fun loadDataAdapter(lstClaims: List<Claim>) {
        claimAdapter.addAllItems(lstClaims)
        claimAdapter.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        Snackbar.make(findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_SHORT).show()
    }

}
