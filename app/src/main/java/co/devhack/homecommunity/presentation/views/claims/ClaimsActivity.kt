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
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import co.devhack.homecommunity.R
import co.devhack.homecommunity.data.db.AppDB
import co.devhack.homecommunity.data.entities.mapper.ClaimEntityMapper
import co.devhack.homecommunity.data.repositories.ClaimCloudSource
import co.devhack.homecommunity.data.repositories.ClaimDBSource
import co.devhack.homecommunity.data.repositories.ClaimRepository
import co.devhack.homecommunity.domain.model.Claim
import co.devhack.homecommunity.domain.usecase.claim.CreateClaimUseCase
import co.devhack.homecommunity.presentation.presenters.claims.ClaimPresenter
import com.afollestad.materialdialogs.MaterialDialog
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_claims.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ClaimsActivity : AppCompatActivity(), IClaimsView, Validator.ValidationListener {

    private lateinit var presenter: ClaimPresenter
    private val PERMISOS = 999
    private val PHOTO_CAMERA = 888
    private var uriPhoto: Uri? = null
    private val SELECT_PICTURE = 1010
    private lateinit var materialDialog: MaterialDialog
    private var validator: Validator? = null
    private lateinit var toolbar: Toolbar

    @NotEmpty(messageResId = R.string.field_required)
    private lateinit var etSubject: EditText

    @NotEmpty(messageResId = R.string.field_required)
    private lateinit var etDescription: EditText

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
                                ClaimCloudSource(),
                                ClaimEntityMapper()
                        )
                ))

        etSubject = findViewById(R.id.txtSubject)
        etDescription = findViewById(R.id.txtDescription)

        btnSave.isEnabled = false
        btnimg.isEnabled = false

        requestPermission()

        btnSave.setOnClickListener { saveClaim() }

        btnimg.setOnClickListener {
            showOptions()
        }

        validator = Validator(this)
        validator?.setValidationListener(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showOptions() {
        val options = mutableListOf<String>(getString(R.string.take_photo), getString(R.string.select_gallery))

        MaterialDialog.Builder(this)
                .title(R.string.select_option)
                .items(options)
                .itemsCallbackSingleChoice(-1) { _, _, which, _ ->
                    if (options[which] == getString(R.string.take_photo)) {
                        openCamera()
                    } else if (options[which] == getString(R.string.select_gallery)) {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        intent.type = "image/*"
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_gallery)), SELECT_PICTURE)
                    }

                    true
                }.show()

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

    override fun showError(error: String) {
        Snackbar.make(findViewById(android.R.id.content),
                error, Snackbar.LENGTH_SHORT).show()
    }

    override fun saveClaim() {
        validator?.validate()
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

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            btnSave.isEnabled = true
            btnimg.isEnabled = true
            return true
        }

        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.lbl_permission,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok,
                            {
                                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        , PERMISOS)
                            }).show()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISOS)
        }

        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISOS) {
            if (grantResults.size == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.lbl_permission_granted,
                        Toast.LENGTH_SHORT).show()

                btnSave.isEnabled = true
                btnimg.isEnabled = true

            } else {
                Toast.makeText(this, R.string.lbl_permission,
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PHOTO_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_CAMERA) {

                val bitmap = data?.extras?.get("data") as Bitmap?

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

                uriPhoto?.let {
                    Picasso.get().load(it).fit().centerCrop().into(btnimg)
                }

            }

            if (requestCode == SELECT_PICTURE) {
                uriPhoto = data?.data
                uriPhoto?.let {
                    Picasso.get().load(it).fit().centerCrop().into(btnimg)
                }

            }
        }
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!.iterator()) {
            val view = error.view
            val message = error.getCollatedErrorMessage(this)

            if (view is EditText) {
                view.error = message
            } else {
                Snackbar.make(findViewById<View>(android.R.id.content),
                        message,
                        Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onValidationSucceeded() {
        if (uriPhoto == null) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.img_mandatory,
                    Snackbar.LENGTH_SHORT).show()
            return
        }

        val subject = etSubject.text.toString()
        val description = etDescription.text.toString()
        val type = spType.selectedItem.toString()

        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())

        val claim = Claim(null, subject, description, type, uriPhoto!!.toString(), timestamp)

        presenter.saveClaim(claim)
    }

}
