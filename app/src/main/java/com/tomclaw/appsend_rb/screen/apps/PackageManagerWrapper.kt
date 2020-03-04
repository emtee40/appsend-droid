package com.tomclaw.appsend_rb.screen.apps

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

interface PackageManagerWrapper {

    fun getInstalledApplications(flags: Int): List<ApplicationInfo>

    fun getPackageInfo(packageName: String, flags: Int): PackageInfo

    fun getApplicationLabel(info: ApplicationInfo): String

    fun getLaunchIntentForPackage(packageName: String): Intent?

}

class PackageManagerWrapperImpl(
        private val packageManager: PackageManager
) : PackageManagerWrapper {

    override fun getInstalledApplications(flags: Int): List<ApplicationInfo> =
            packageManager.getInstalledApplications(flags)

    override fun getPackageInfo(packageName: String, flags: Int): PackageInfo =
            packageManager.getPackageInfo(packageName, flags)

    override fun getApplicationLabel(info: ApplicationInfo) = packageManager.getApplicationLabel(info).toString()

    override fun getLaunchIntentForPackage(packageName: String): Intent? =
            packageManager.getLaunchIntentForPackage(packageName)

}
