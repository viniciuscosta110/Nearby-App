package com.example.nearby.ui.screen.qrcode_scanner

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.nearby.MainActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale

@Composable
fun QRCodeScannerScreen(modifier: Modifier = Modifier, onCompletedScan: (String) -> Unit) {
    val context = LocalContext.current
    val scanOptions = ScanOptions().setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        .setPrompt("Leia o QRCode do Cupom").setCameraId(0).setBeepEnabled(false)
        .setOrientationLocked(false).setBarcodeImageEnabled(true)

    val barcodeLauncher = rememberLauncherForActivityResult(
        ScanContract(),
    ) { result ->
        Log.d("QRCodeScanner", "QRCode content: $result")
        onCompletedScan(result.contents.orEmpty())
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (!isGranted) ActivityResultContracts.RequestPermission()
        else barcodeLauncher.launch(scanOptions)
    }

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            barcodeLauncher.launch(scanOptions)
        } else if (shouldShowRequestPermissionRationale(
                context as MainActivity,
                android.Manifest.permission.CAMERA
            )
        ) {
            Toast.makeText(
                context,
                "Necessário permitir o acesso à câmera para continuar.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(true) {
        checkCameraPermission()
        Log.d("DESGRAÇA", "DESGRAÇA")
    }

    Column(modifier = modifier.fillMaxSize()) {}
}