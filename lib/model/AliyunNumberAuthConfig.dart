import 'package:aliyun_number_auth/model/AliyunNumberAuthUIConfig.dart';

/// 号码认证配置类
class AliyunNumberAuthConfig {
  /// 安卓密钥
  final String androidKey;

  /// ios密钥
  final String iosKey;

  /// 是否显示日志，默认false
  bool? log = false;

  /// UI配置类
  AliyunNumberAuthUIConfig? aliyunNumberAuthUIConfig;

  /// 实例化配置
  ///
  /// [androidKey] 安卓密钥
  /// [iosKey] ios密钥
  /// [log] 是否显示日志，默认false
  AliyunNumberAuthConfig(
      {required this.androidKey,
      required this.iosKey,
      this.log = false,
      this.aliyunNumberAuthUIConfig});

  /// 将对象转换为 JSON 格式的 Map
  Map<String, dynamic> toJson() {
    return {'androidKey': androidKey, 'iosKey': iosKey, 'log': log};
  }
}
