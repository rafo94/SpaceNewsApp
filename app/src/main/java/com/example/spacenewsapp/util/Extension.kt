package com.example.spacenewsapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.spacenewsapp.util.Constants.Companion.FACEBOOK_URL
import com.example.spacenewsapp.util.Constants.Companion.GITHUB_URL
import com.example.spacenewsapp.util.Constants.Companion.LINKEDIN_URL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


fun String.openWebLink(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(this)
    context.startActivity(intent)
}

@SuppressLint("SimpleDateFormat")
fun String.convertDate(): String {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyy", Locale.ENGLISH)
        val date = LocalDate.parse(this, inputFormatter)
        outputFormatter.format(date)
    } else {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("dd MMM yyyy")
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    }
}

fun facebookIntent(pm: PackageManager): Intent {
    var uri = Uri.parse(FACEBOOK_URL)
    try {
        val applicationInfo: ApplicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo.enabled) {
            uri = Uri.parse("fb://facewebmodal/f?href=$FACEBOOK_URL")
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
    }

    return Intent(Intent.ACTION_VIEW, uri)
}

@SuppressLint("QueryPermissionsNeeded")
fun linkedinIntent(pm: PackageManager): Intent {
    var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://$LINKEDIN_URL"))
    val list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    if (list.isEmpty()) {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=$LINKEDIN_URL"))
    }
    return intent
}

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenStarted { this@launchWhenStarted.collect() }
}