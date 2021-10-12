package com.chuangdun.flutter.plugin.printer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


private const val TAG = "PrinterPlugin"

/** PrinterPlugin */
class PrinterPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "printer")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        Log.i(TAG, "打印机调用:${call.method}, 参数:${call.arguments}")
        if (call.method == "print") {
            val arguments = call.arguments as Map<*, *>
            val filePath = arguments["filePath"] as String
            val bannerTitle = arguments["bannerTitle"] as String
            when (val provider = arguments["provider"] as String) {
                "Lexmark" -> {
                    val intent = Intent(context, LexmarkPrinterActivity::class.java)
                    intent.putExtra("filePath", filePath)
                    intent.putExtra("bannerTitle", bannerTitle)
                    context.startActivity(intent)
                }
                "PrinterShare" -> {
                    val intent: Intent? = PrinterShare().getPrintIntent(context, filePath)
                    if (intent == null) {
                        Toast.makeText(context, "打印失败,文件不存在或格式不支持", Toast.LENGTH_LONG).show()
                    } else {
                        context.startActivity(intent)
                    }
                }
                else -> {
                    Toast.makeText(context, "不支持的打印机提供商:$provider", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
