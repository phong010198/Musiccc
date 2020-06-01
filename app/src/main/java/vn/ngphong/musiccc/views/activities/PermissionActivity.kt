package vn.ngphong.musiccc.views.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

open class PermissionActivity : AppCompatActivity() {

    private val permissionRead = Manifest.permission.READ_EXTERNAL_STORAGE
    private val permissionForeground = Manifest.permission.FOREGROUND_SERVICE
    private var REQUEST_CODE_ASK_PERMISSIONS = 123
    private var permissionsGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (!permissionsGranted) {
            this.finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionsGranted = true
            } else {
                this.finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    permissionRead
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    applicationContext,
                    permissionRead
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(permissionRead, permissionForeground),
                    REQUEST_CODE_ASK_PERMISSIONS
                )
                permissionsGranted = false
            }
            permissionsGranted = true
        } else {
            permissionsGranted = true
        }
    }
}
