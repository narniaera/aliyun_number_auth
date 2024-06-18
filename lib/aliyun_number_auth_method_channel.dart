import 'package:aliyun_number_auth/model/AliyunNumberAuthConfig.dart';
import 'package:aliyun_number_auth/model/AliyunNumberAuthUIConfig.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'aliyun_number_auth_platform_interface.dart';

/// An implementation of [AliyunNumberAuthPlatform] that uses method channels.
class MethodChannelAliyunNumberAuth extends AliyunNumberAuthPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('aliyun_number_auth');
  late final AliyunNumberAuthConfig _aliyunNumberAuthConfig;
  late final Function setUIClickListenerCallback;
  late String token = "";

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> init(AliyunNumberAuthConfig config) async {
    methodChannel.setMethodCallHandler(_handleMethod);
    _aliyunNumberAuthConfig = config;
    try {
      await methodChannel.invokeMethod(
          'init', _aliyunNumberAuthConfig.toJson());
      if (config.log!) {
        print('初始化成功！');
      }
    } catch (e) {
      if (config.log!) {
        print(e);
      }
    }
  }

  @override
  Future<void> setUIConfig(AliyunNumberAuthUIConfig config) async {
    methodChannel.setMethodCallHandler(_handleMethod);
    await methodChannel.invokeMethod('setUIConfig', config.toJson());
  }

  @override
  Future<String?> getCurrentCarrierName() async {
    final carrierName =
        await methodChannel.invokeMethod('getCurrentCarrierName');
    if (_aliyunNumberAuthConfig.log!) {
      if (carrierName == "CMCC") {
        print('CMCC(中国移动)');
      } else if (carrierName == "CUCC") {
        print('CUCC(中国联通)');
      } else {
        print('CTCC(中国电信)');
      }
    }
    return carrierName;
  }

  @override
  Future<String> getVersion() async {
    final version = await methodChannel.invokeMethod('getVersion');
    if (_aliyunNumberAuthConfig.log!) {
      print('SDK: ' + version);
    }
    return version;
  }

  @override
  Future<void> setUIClickListener(Function callback) async {
    setUIClickListenerCallback = callback;
  }

  @override
  Future<void> getLoginToken(int timeout) async {
    methodChannel.invokeMethod('getLoginToken', {timeout: timeout});
  }

  @override
  Future<void> quitLoginPage() async {
    await methodChannel.invokeMethod('quitLoginPage');
  }

  @override
  Future<void> logined() async {
    await methodChannel.invokeMethod('logined');
  }

  Future<void> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case 'onTokenSuccess':
        final result = call.arguments as Map;
        final code = result['code'] as String;
        if (code == "600000") {
          //获取token成功
          final params = {
            'code': '600000',
            'token': result['token'] as String,
          };
          setUIClickListenerCallback(params);
        }

        if (_aliyunNumberAuthConfig.log!) {
          // 处理成功获取到的token
          print('Token received: $code');
        }
        break;
      case 'onTokenFailed':
        if (_aliyunNumberAuthConfig.log!) {
          final errorMessage = call.arguments;
          if (_aliyunNumberAuthConfig.log!) {
            // 处理获取token失败的情况
            print('Token failed: $errorMessage');
          }
          break;
        }
      case 'setAuthListener':
        if (_aliyunNumberAuthConfig.log!) {
          final result = call.arguments as Map;
          final params = {
            'code': result['code'] as String,
            'jsonString': result['jsonString'] as String
          };
          setUIClickListenerCallback(params);
          if (_aliyunNumberAuthConfig.log!) {
            // 处理获取token失败的情况
            print('setAuthListener: $result');
          }
          break;
        }
      default:
        final log = call.arguments;
        print('log $log');
    }
  }
}
