package com.junkyard.simplelauncher.ui.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable,
//    val versionName: String,
//    val versionCode: Int,
    val isSystemApp: Boolean,
    val isInstalled: Boolean
)