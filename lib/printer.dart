import 'dart:async';

import 'package:flutter/services.dart';

class Printer {
  static const MethodChannel _channel = const MethodChannel('printer');

  ///打印文档.
  ///[filePath]文档文件路径.
  ///[bannerTitle]打印页面的标题.
  ///[provider]打印机提供商,取值[Lexmark,PrinterShare],默认Lexmark
  static Future<void> print(String filePath, String bannerTitle, {String provider = "Lexmark"}) async {
    assert(filePath != null);
    assert(bannerTitle != null);
    assert(provider != null);
    await _channel.invokeMethod('print', {"filePath": filePath, "bannerTitle": bannerTitle, "provider": provider});
  }
}
