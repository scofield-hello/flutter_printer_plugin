import 'dart:io';

import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';
import 'package:printer/printer.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              FlatButton.icon(
                  onPressed: () async {
                    var dir = await getExternalStorageDirectory();
                    var file = File("${dir.path}/test_print.pdf");
                    Printer.print(file.path, "廊坊市司法局自助终端", provider: "PrinterShare");
                  },
                  icon: Icon(Icons.print),
                  label: Text("PrinterShare打印")),
              FlatButton.icon(
                  onPressed: () async {
                    var dir = await getExternalStorageDirectory();
                    var file = File("${dir.path}/test_print.pdf");
                    Printer.print(file.path, "廊坊市司法局自助终端", provider: "Lexmark");
                  },
                  icon: Icon(Icons.print),
                  label: Text("Lexmark打印")),
            ],
          ),
        ),
      ),
    );
  }
}
