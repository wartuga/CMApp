package com.cmapp

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.cmapp.model.data.DataBaseHelper
import com.cmapp.model.data.StorageHelper.setUsername
import com.cmapp.navigation.NavGraph
import com.cmapp.ui.theme.DarkColorScheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val customFontFamily = FontFamily(
    Font(resId = R.font.harry) // Replace with your font file name
)


@Composable
fun MyAppTheme(content: @Composable () -> Unit) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Black)

    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            bodyLarge = TextStyle(
                fontFamily = customFontFamily,
            ),
            titleLarge = TextStyle(
                fontFamily = customFontFamily,
            )
        ),
        content = content,
        colorScheme = DarkColorScheme
    )
}

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
            Log.d("Permission", "Notification permission granted.")
        } else {
            // Inform the user that the app cannot show notifications without the permission.
            Log.d("Permission", "Notification permission denied.")
            Toast.makeText(
                this,
                "Notification permission is required to receive notifications.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUsername(this, "Test") //TESTE

        //FRIEND TEST
        //addLearnedPotion("FriendTest", "-OGyy3IfmE71PjebDJVE")
        //addLearnedSpell("FriendTest", "-OGvCKrwmDtN0fp9S59J")

        DataBaseHelper.addNotificationListener{ username, spell ->
            Toast.makeText(
                this,
                "$username unlocked ${spell.name}",
                Toast.LENGTH_LONG
            ).show()
        }

        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                askNotificationPermission()
                NavGraph (
                    navController,
                    this
                )
            }
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                // Check if the permission is already granted
                ContextCompat.checkSelfPermission(
                    this,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // FCM SDK (and your app) can post notifications.
                    Log.d("Permission", "Notification permission already granted.")
                }

                // Check if the user should see a rationale UI for this permission
                shouldShowRequestPermissionRationale(POST_NOTIFICATIONS) -> {
                    // Display an educational UI explaining why the app needs this permission
                    AlertDialog.Builder(this)
                        .setTitle("Notification Permission Needed")
                        .setMessage("This app requires notification permission to alert you about updates and events.")
                        .setPositiveButton("OK") { _, _ ->
                            // Request the permission after showing the rationale
                            requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                        }
                        .setNegativeButton("No Thanks") { _, _ ->
                            // Allow the user to continue without granting permission
                            Toast.makeText(
                                this,
                                "You can enable notifications later in settings.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .show()
                }

                else -> {
                    // Directly request the permission
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        }
    }
}