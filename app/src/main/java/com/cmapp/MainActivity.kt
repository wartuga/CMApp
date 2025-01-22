package com.cmapp

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.cmapp.model.data.DataBaseHelper
import com.cmapp.model.data.StorageHelper.setUsername
import com.cmapp.navigation.NavGraph
import com.cmapp.ui.theme.DarkColorScheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.UUID

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
    var perm = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
            Log.d("Permission", "Notification permission granted.")
            perm = true
        } else {
            // Inform the user that the app cannot show notifications without the permission.
            Log.d("Permission", "Notification permission denied.")
            Toast.makeText(
                this,
                "Notification permission is required to receive notifications.",
                Toast.LENGTH_LONG
            ).show()
            perm = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUsername(this, "Test") //TESTE

        askNotificationPermission()

        if(perm){
            createNotificationChannel()
        }

        //FRIEND TEST
        //addLearnedPotion("FriendTest", "-OGyy3IfmE71PjebDJVE")
        //addLearnedSpell("FriendTest", "-OGvCKrwmDtN0fp9S59J")

        // Push Notification
        DataBaseHelper.addNotificationListener{ username, spellName ->
            showNotification("$username unlocked $spellName")
        }

        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
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
                    perm = true
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
                            perm = true
                        }
                        .setNegativeButton("No Thanks") { _, _ ->
                            // Allow the user to continue without granting permission
                            Toast.makeText(
                                this,
                                "You can enable notifications later in settings.",
                                Toast.LENGTH_LONG
                            ).show()
                            perm = false
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelName = "Default Channel"
            val channelDescription = "This is the default notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("MissingPermission")
    private fun showNotification(message: String) {
        val uuid = UUID.randomUUID().hashCode()
        val channelId = "default_channel_id" // Ensure this matches the channel you created

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_ha_round) // Replace with your app's icon
            .setContentTitle("Stronger wizard")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Priority for devices < API 26
            .setAutoCancel(true) // Removes the notification when clicked

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(uuid, notificationBuilder.build())
    }
}