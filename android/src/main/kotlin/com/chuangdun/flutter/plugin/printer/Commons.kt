package com.chuangdun.flutter.plugin.printer

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun getUriFromFilePath(context:Context, filePath:String):Uri?{
    val app = context.applicationContext
    val file = File(filePath)
    return FileProvider.getUriForFile(app, "${app.packageName}.provider", file)
}