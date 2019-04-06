import 'package:flutter/services.dart';
import 'commomnkey.dart';

class AlertBox {
  static const MethodChannel _channel = const MethodChannel(Comman.alert_box);
  static var length_short = "0";
  static var length_long = "1";
  static var Error = "0";
  static var Debug = "1";
  static var Verbose = "2";

  static Toast(String message, String type) {
    _channel.invokeMethod(
        Comman.toster, {Comman.message: message, Comman.type: type});
  }

  static Log(int type, String key, String message) {
    _channel.invokeMethod(Comman.logger,
        {Comman.type: type, Comman.key: key, Comman.message: message});
  }

  static bool Netconnected() {
    _channel.invokeMethod(Comman.net);
  }
}
