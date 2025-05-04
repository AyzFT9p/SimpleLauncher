package com.junkyard.simplelauncher.platform

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build


fun PackageManager.getInstalledApps(flags: Int = 0): List<ApplicationInfo> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(flags.toLong()))
    } else {
        this.getInstalledApplications(flags)
    }
}

fun PackageManager.getLauncherApps(flags: Int = 0): List<ApplicationInfo> {
    val installedApps = this.getInstalledApps(flags)

    return installedApps.filter { appInfo ->
        this.getLaunchIntentForPackage(appInfo.packageName) != null
    }
}

fun PackageManager.getIntent(packageName: String): Intent?{
    return this.getLaunchIntentForPackage(packageName)
}