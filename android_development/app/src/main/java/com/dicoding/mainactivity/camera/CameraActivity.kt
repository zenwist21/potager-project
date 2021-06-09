package com.dicoding.mainactivity.camera

import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.mainactivity.R
import com.dicoding.mainactivity.databinding.ActivityCameraBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        outputDirectory = getOutputDirectory()
        //camera
        if (allPermissionGranted()) {
            Toast.makeText(this, "Access Granted", Toast.LENGTH_SHORT).show()
            startCamera()
        }
        else{
            ActivityCompat.requestPermissions(
                this, Constant.REQUIRED_PERMISSION,
                Constant.REQUEST_CODE_PERMISSION
            )
        }
        binding.btnCamera.setOnClickListener {
            takePhoto()
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it , resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if(mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(Constant.FILE_NAME_FORMAT,
            Locale.getDefault())
                .format(
                System.currentTimeMillis())+ ".jpg")
        val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()
        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val message = "Saved to Gallery"
                    Toast.makeText(this@CameraActivity, " $savedUri $message" , Toast.LENGTH_SHORT).show()

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(Constant.TAG, "OnError :  ${exception.message}", exception)
                }

            }
        )


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
      if(requestCode == Constant.REQUEST_CODE_PERMISSION){
          if(allPermissionGranted()){
            startCamera()
          }
          else
          {
              Toast.makeText(this,
                  "Access is not Granted, please Allow camera on your device",Toast.LENGTH_SHORT)
                  .show()
              finish()
          }

      }
    }

    private fun startCamera() {
        val cameraProvideFuture = ProcessCameraProvider.getInstance(this)
        cameraProvideFuture.addListener({
            val cameraProvider : ProcessCameraProvider = cameraProvideFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {

                    it.setSurfaceProvider(
                        binding.identifyCamera.surfaceProvider

                    )
                }
            imageCapture = ImageCapture.Builder()
                .build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector ,
                    preview, imageCapture)

            }
            catch (e:Exception){
                Log.d(Constant.TAG , "Can not connect to camera", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionGranted() =
        Constant.REQUIRED_PERMISSION.all{
            ContextCompat.checkSelfPermission(
            baseContext, it
            )== PackageManager.PERMISSION_GRANTED
        }

}