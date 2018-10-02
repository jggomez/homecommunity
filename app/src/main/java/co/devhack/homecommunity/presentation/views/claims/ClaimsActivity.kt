package co.devhack.homecommunity.presentation.views.claims

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import co.devhack.homecommunity.R
import co.devhack.homecommunity.data.db.AppDB
import co.devhack.homecommunity.data.entities.mapper.ClaimEntityMapper
import co.devhack.homecommunity.data.repositories.ClaimDBSource
import co.devhack.homecommunity.data.repositories.ClaimRepository
import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.usecase.claim.CreateClaimUseCase
import co.devhack.homecommunity.presentation.presenters.claims.ClaimPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_claims.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ClaimsActivity : AppCompatActivity(), IClaimsView {

    private val progress = progressBar
    private lateinit var presenter: ClaimPresenter
    val PERMISOS = 999
    val PHOTO_CAMERA = 888
    private lateinit var uriPhoto : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claims)

        presenter = ClaimPresenter(this,
                CreateClaimUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        ClaimRepository(
                                ClaimDBSource(
                                        AppDB.getInstance(this)!!.ClaimDAO()
                                ),
                                ClaimEntityMapper()
                        )
                ))

        btnSave.isEnabled = !requestPermission()

    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showError(error: String) {
        Snackbar.make(findViewById(android.R.id.content),
                error, Snackbar.LENGTH_SHORT).show()
    }

    override fun saveClaim() {
        val subject = txtSubject.text.toString()
        val description = txtDescription.toString()
        val type = spType.selectedItem.toString()

        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())


        val claim = Claim(null, subject, description, type, "", timestamp)

        presenter.saveClaim(claim)
    }

    override fun gotoClaimList() {
        finish()
    }

    override fun showIdClaim(id: Int) {
        Toast.makeText(this,
                R.string.lbl_claim_id + id,
                Toast.LENGTH_SHORT).show()
    }

    override fun disableViews() {
        txtSubject.isEnabled = false
        txtDescription.isEnabled = false
        spType.isEnabled = false
        btnimg.isEnabled = false
        btnSave.isEnabled = false
    }

    override fun enableViews() {
        txtSubject.isEnabled = true
        txtDescription.isEnabled = true
        spType.isEnabled = true
        btnimg.isEnabled = true
        btnSave.isEnabled = true
    }

    private fun requestPermission(): Boolean {

        if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {
            return true
        }

        if ((shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                ||
                (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.lbl_permission,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok,
                            {
                                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA), PERMISOS)
                            })
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA), PERMISOS)
        }

        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISOS) {
            if (grantResults.size == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.lbl_permission_granted,
                        Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.lbl_permission,
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun openCamera() {

        val MEDIA_DIRECTOTY = "home_community"
        val file = File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTOTY)
        var isDirectoryCreated = false

        if (!file.exists()) {
            isDirectoryCreated = file.mkdirs()
        }

        if (isDirectoryCreated) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, PHOTO_CAMERA)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_CAMERA) {
                val bitmap = data?.getStringExtra("data") as Bitmap?
                val storageDir = baseContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

                val timestamp = System.currentTimeMillis() / 1000
                val image = File.createTempFile(
                        "img_$timestamp",
                        ".jpg",
                        storageDir
                )

                uriPhoto = Uri.fromFile(image)

                val os = FileOutputStream(image)
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, os)
                // picasso colocarla en el boton de imagen
            }
        }
    }

}
