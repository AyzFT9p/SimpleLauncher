package com.junkyard.simplelauncher

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.junkyard.simplelauncher.platform.getIntent
import com.junkyard.simplelauncher.platform.getLauncherApps
import com.junkyard.simplelauncher.ui.model.AppInfo
import com.junkyard.simplelauncher.ui.screen.MainScreen
import com.junkyard.simplelauncher.ui.theme.SimpleLauncherTheme
import com.junkyard.simplelauncher.ui.utils.Throttle

class MainActivity : ComponentActivity() {
    private val pm: PackageManager by lazy {
        applicationContext.packageManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appInfos = pm.getLauncherApps()

        val launcherApps = appInfos.filter { appInfo ->
            pm.getIntent(appInfo.packageName) != null
        }.map { appInfo ->
            AppInfo(
                appName = pm.getApplicationLabel(appInfo).toString(),
                packageName = appInfo.packageName,
                icon = pm.getApplicationIcon(appInfo),
//                versionName = pm.getPackageInfo(appInfo.packageName, 0).versionName,
//                versionCode = pm.getPackageInfo(appInfo.packageName, 0).versionCode,
                isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                isInstalled = true
            )
        }

        enableEdgeToEdge()
        setContent {
            BackHandler(true) {
                Log.d("MainActivity", "Back button pressed")

                Throttle(5_000L) {
                    Toast.makeText(this, "This is a dead end, dude.", Toast.LENGTH_SHORT).show()
                }
            }


            SimpleLauncherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

//                    Log.d("MainActivity", "Installed apps count: ${appInfos.size}")
//                    Log.d("MainActivity", "Installed apps for launcher count: ${launcherApps.size}")

                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        apps = launcherApps,
                        onClick = { appInfo ->
                            Log.d("MainActivity", "App clicked: ${appInfo.appName}")
                            pm.getIntent(appInfo.packageName)?.let { intent ->
                                runCatching {
                                    startActivity(intent)
                                }.onFailure {
                                    Log.e(
                                        "MainActivity",
                                        "Failed to start activity for app: ${appInfo.appName}. With intent: $intent",
                                        it
                                    )
                                    Toast.makeText(
                                        this,
                                        "Failed to open app: ${appInfo.appName}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}