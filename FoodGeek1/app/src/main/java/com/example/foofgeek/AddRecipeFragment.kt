package com.example.foofgeek

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.foofgeek.Helper.ImageHelper
import java.io.IOException

class AddRecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)
        retainInstance = true
        var imm: InputMethodManager? = null
        var recipeName: TextView? = null
        var author: TextView? = null
        var country: Spinner? = null
        var description: EditText? = null
        var camera: ImageButton? = null

        //set userid
        val pref = view.context.getSharedPreferences("Login", Activity.MODE_PRIVATE)
        val userID = pref.getString("userID", "")
        author = view.findViewById(R.id.txt_Add_Author)
        author.setText(userID)

        //hidding keybord
        imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        description = view.findViewById(R.id.txt_Add_Description)
        recipeName = view.findViewById(R.id.txt_Add_NewName)
        val root = view.findViewById<RelativeLayout>(R.id.root_Add_Recipe)
        root.setOnClickListener {
            imm!!.hideSoftInputFromWindow(description.getWindowToken(), 0)
            imm!!.hideSoftInputFromWindow(recipeName.getWindowToken(), 0)
        }
        camera = view.findViewById(R.id.btn_Add_Camera)
        var mPhotoImageView: ImageView? = null
        mPhotoImageView = view.findViewById(R.id.imgv_Add_Image)
        //Check and ask for permissions in version Android API 23 and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions()
        }
        camera.setOnClickListener(View.OnClickListener {
            val cameraListener =
                DialogInterface.OnClickListener { dialog, which -> doTakePhotoAction() }
            val albumListener =
                DialogInterface.OnClickListener { dialog, which -> doTakeAlbumAction() }
            val cancelListener =
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }
            AlertDialog.Builder(context)
                .setTitle("Select your Image from")
                .setPositiveButton("Take Picture", cameraListener)
                .setNeutralButton("Gallery", albumListener)
                .setNegativeButton("Cancel", cancelListener)
                .show()
        })
        return view
    }

    private fun doTakePhotoAction() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_FROM_CAMERA)
    }

    private fun doTakeAlbumAction() {
        // Call to photo gallery
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val mImageCaptureUri: Uri? = null
        var mPhotoImageView: ImageView? = null
        var imageHelper = ImageHelper()
        if (resultCode == Activity.RESULT_OK) {
            //from camera
            if (requestCode == PICK_FROM_CAMERA) {
                val extra = data!!.extras
                if (extra != null) {
                    val photo = extra.getParcelable<Bitmap>("data")
                    mPhotoImageView!!.setImageBitmap(imageHelper.resizeImage(photo))
                }
            } else  //from photo gallery
            {
                val imageUri = data!!.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(activity!!.contentResolver, imageUri)
                    mPhotoImageView!!.setImageBitmap(imageHelper.resizeImage(bitmap))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkPermissions() {
        if ((ContextCompat.checkSelfPermission(
                this.context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this.context!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                this.context!!,
                Manifest.permission.CAMERA
            )
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this.activity!!, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                1052
            )
        }
    }

    companion object {
        //variable for camera
        private const val PICK_FROM_CAMERA = 0
        private const val PICK_FROM_ALBUM = 1
    }
}