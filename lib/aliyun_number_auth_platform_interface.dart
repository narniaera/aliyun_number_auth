import 'package:aliyun_number_auth/model/AliyunNumberAuthConfig.dart';
import 'package:aliyun_number_auth/model/AliyunNumberAuthUIConfig.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'aliyun_number_auth_method_channel.dart';

abstract class AliyunNumberAuthPlatform extends PlatformInterface {
  /// Constructs a AliyunNumberAuthPlatform.
  AliyunNumberAuthPlatform() : super(token: _token);

  static final Object _token = Object();

  static AliyunNumberAuthPlatform _instance = MethodChannelAliyunNumberAuth();

  /// The default instance of [AliyunNumberAuthPlatform] to use.
  ///
  /// Defaults to [MethodChannelAliyunNumberAuth].
  static AliyunNumberAuthPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AliyunNumberAuthPlatform] when
  /// they register themselves.
  static set instance(AliyunNumberAuthPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> init(AliyunNumberAuthConfig config) {
    throw UnimplementedError('init() has not been implemented.');
  }

  Future<void> setUIConfig(AliyunNumberAuthUIConfig config) {
    throw UnimplementedError('init() has not been implemented.');
  }

  Future<String?> getCurrentCarrierName() {
    throw UnimplementedError(
        'getCurrentCarrierName() has not been implemented.');
  }

  Future<String> getVersion() {
    throw UnimplementedError('getVersion() has not been implemented.');
  }

  Future<void> setUIClickListener(Function callback) {
    throw UnimplementedError('setAuthListener() has not been implemented.');
  }

  Future<void> getLoginToken(int timeout) {
    throw UnimplementedError('setAuthListener() has not been implemented.');
  }

  Future<void> quitLoginPage() {
    throw UnimplementedError('quitLoginPage() has not been implemented.');
  }

  Future<void> logined() {
    throw UnimplementedError('logined() has not been implemented.');
  }
}
