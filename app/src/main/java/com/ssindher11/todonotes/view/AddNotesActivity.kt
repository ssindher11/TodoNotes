package com.ssindher11.todonotes.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.ssindher11.todonotes.BuildConfig
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.utils.AppConstant
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    private lateinit var titleET: EditText
    private lateinit var descET: EditText
    private lateinit var submitBtn: MaterialButton
    private lateinit var notesIV: ImageView
    private lateinit var addImageBtn: MaterialButton

    private val REQUEST_CODE_GALLERY = 1008
    private val REQUEST_CODE_CAMERA = 1009
    private val PERMISSIONS_CODE = 77
    private var picturePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
        setListeners()
    }

    private fun bindViews() {
        titleET = findViewById(R.id.et_title)
        descET = findViewById(R.id.et_description)
        submitBtn = findViewById(R.id.btn_submit)
        notesIV = findViewById(R.id.iv_notes)
        addImageBtn = findViewById(R.id.btn_add_image)
    }

    private fun setListeners() {
        addImageBtn.setOnClickListener {
            if (checkAndRequestPermission()) {
                setupDialog()
            }
        }
        submitBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra(AppConstant.TITLE, titleET.text.toString())
            intent.putExtra(AppConstant.DESCRIPTION, descET.text.toString())
            intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setupDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_selector, null)
        val cameraTV: TextView = view.findViewById(R.id.tv_camera)
        val galleryTV: TextView = view.findViewById(R.id.tv_gallery)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create()

        cameraTV.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photofile: File? = null
            photofile = createImage()
            if (photofile != null) {
                val photoURI = FileProvider.getUriForFile(this@AddNotesActivity, BuildConfig.APPLICATION_ID + ".provider", photofile)
                picturePath = photofile.absolutePath
                Log.d("ppath", picturePath)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                dialog.hide()
            }
        }
        galleryTV.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_GALLERY)
            dialog.hide()
        }

        dialog.show()
    }

    private fun createImage(): File? {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_" + timestamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val selectedImage = data?.data
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(selectedImage!!, filePath, null, null, null)
                    cursor?.moveToFirst()
                    val columnIndex = cursor?.getColumnIndex(filePath[0])
                    picturePath = cursor?.getString(columnIndex!!)!!
                    cursor.close()
                    Glide.with(this).load(picturePath).into(notesIV)
                    addImageBtn.visibility = View.GONE
                }
                REQUEST_CODE_CAMERA -> {
                    Glide.with(this).load(picturePath).into(notesIV)
                    addImageBtn.visibility = View.GONE
                }
            }
        }
    }

    private fun checkAndRequestPermission(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray(), PERMISSIONS_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_CODE -> {
                if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setupDialog()
                }
            }
        }
    }
}
