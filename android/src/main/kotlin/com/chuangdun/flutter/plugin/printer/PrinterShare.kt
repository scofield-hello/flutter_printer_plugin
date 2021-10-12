package com.chuangdun.flutter.plugin.printer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri

private const val PRINTER_SHARE_PKG = "com.dynamixsoftware.printershare"
private const val PRINT_PDF_ACTIVITY = "com.dynamixsoftware.printershare.ActivityPrintPDF"
private const val PRINT_DOCUMENT_ACTIVITY = "com.dynamixsoftware.printershare.ActivityPrintDocuments"
private const val PRINT_PICTURE_ACTIVITY = "com.dynamixsoftware.printershare.ActivityPrintPictures"
private const val PRINT_WEB_ACTIVITY = "com.dynamixsoftware.printershare.ActivityWeb"

class PrinterShare {
    fun getPrintIntent(context: Context, filePath: String): Intent? {
        val suffix = filePath.split(".").last().toLowerCase()
        val data: Uri = getUriFromFilePath(context, filePath) ?: return null
        val intent = Intent(Intent.ACTION_VIEW)
        var comp: ComponentName?
        when (suffix) {
            in listOf("pdf") -> {
                intent.setDataAndType(data, "application/pdf")
                comp = ComponentName(PRINTER_SHARE_PKG, PRINT_PDF_ACTIVITY)
            }
            in listOf("doc", "docx", "txt") -> {
                intent.setDataAndType(data, "application/doc")
                comp = ComponentName(PRINTER_SHARE_PKG, PRINT_DOCUMENT_ACTIVITY)
            }
            in listOf("jpg", "jpeg", "gif", "png") -> {
                intent.setDataAndType(data, "image/jpeg")
                comp = ComponentName(PRINTER_SHARE_PKG, PRINT_PICTURE_ACTIVITY)
            }
            in listOf("html", "htm") -> {
                intent.setDataAndType(data, "text/html")
                comp = ComponentName( PRINTER_SHARE_PKG, PRINT_WEB_ACTIVITY)
            }
            else -> return null
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.component = comp
        return intent
    }
}