package vn.ngphong.musiccc.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import vn.ngphong.musiccc.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val permissionCode = 1998

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.FOREGROUND_SERVICE
            )
        }
        checkPermission()
    }

    private fun checkPermission() {
        when (isPermissionsGranted(this)) {
            true -> openMainActivity()
            false -> requestPermissions()
        }
    }

    private fun isPermissionsGranted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            permissions
                .filter { context.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
                .forEach { Log.d("SplashActivity", "$it not granted"); return false }
            return true
        }
        return true
    }

    private fun openMainActivity() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
            finish()
        }, 2000)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode == permissionCode && isPermissionsGranted(this)) {
            true -> openMainActivity()
            else -> finish()
        }
    }
}