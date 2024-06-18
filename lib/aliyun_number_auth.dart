import 'package:aliyun_number_auth/model/AliyunNumberAuthConfig.dart';
import 'package:aliyun_number_auth/model/AliyunNumberAuthUIConfig.dart';

import 'aliyun_number_auth_platform_interface.dart';

class AliyunNumberAuth {
  Future<String?> getPlatformVersion() {
    return AliyunNumberAuthPlatform.instance.getPlatformVersion();
  }

  /// 初始化
  Future<void> init(AliyunNumberAuthConfig config) {
    // ignore: void_checks
    return AliyunNumberAuthPlatform.instance.init(config);
  }

  /// 设置UI配置
  Future<void> setUIConfig(AliyunNumberAuthUIConfig config) {
    // ignore: void_checks
    return AliyunNumberAuthPlatform.instance.setUIConfig(config);
  }

  /// 控件点击事件回调
  Future<void> setUIClickListener(Function callback) {
    return AliyunNumberAuthPlatform.instance.setUIClickListener(callback);
  }

  /// 获取运营商
  /// CMCC(中国移动)、CUCC(中国联通)、CTCC(中国电信)
  Future<String?> getCurrentCarrierName() {
    return AliyunNumberAuthPlatform.instance.getCurrentCarrierName();
  }

  /// 获取运营商版本号
  Future<String> getVersion() {
    return AliyunNumberAuthPlatform.instance.getVersion();
  }

  /// 一键登录获取Token
  Future<void> getLoginToken(int timeout) {
    return AliyunNumberAuthPlatform.instance.getLoginToken(timeout);
  }

  /// 注销登录页面
  Future<void> quitLoginPage() {
    return AliyunNumberAuthPlatform.instance.quitLoginPage();
  }

  /// 注销登录页面
  Future<void> logined() {
    return AliyunNumberAuthPlatform.instance.logined();
  }
}
