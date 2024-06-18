import 'package:aliyun_number_auth/model/AliyunNumberAuthConfig.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:aliyun_number_auth/aliyun_number_auth.dart';
import 'package:aliyun_number_auth/model/AliyunNumberAuthUIConfig.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _aliyunNumberAuthPlugin = AliyunNumberAuth();

  @override
  void initState() {
    super.initState();

    _init();
  }

  /// 初始化号码认证
  _init() async {
    ///sdk配置
    AliyunNumberAuthConfig aliyunNumberAuthConfig = AliyunNumberAuthConfig(
        androidKey:
            "0FonLijJXgPflm0rHfBXCp0bZO0e07gVQn/oQeAeZVgI0UmiyICJQOrci85A0JOlAOq2lXV95QShZynnhUwHh+ksTCtxWLzKghf14kI6G2Poo6+dZSu5mu/vh4chY7gIX4WQ9brTR6bZl3y0uzm9y1CMWCTXkFp2tJLYGC2zEEvzXrrIAxw7FuGQ2EMBa0w8O1NJeFCIjbIJa28jEWMmJDi5NzsnMm19RK7HUDgT0HVmKSvai5HocEDnUXBJmuqDrnydvA78dnBP9eEIaDNNPlq9sU1dvgGR9NCTUEqw6JuVOrlmv+ejgP16Azkh0LV3GpmFAoh9Sps=",
        iosKey: "1",
        log: true);

    ///初始化
    _aliyunNumberAuthPlugin.init(aliyunNumberAuthConfig);
    //定义ui配置
    AliyunNumberAuthUIConfig aliyunNumberAuthUIConfig =
        AliyunNumberAuthUIConfig(
      setAppPrivacyOne: [
        '《用户协议》',
        'https://petstore.deering.cn/html/user_agreement.html'
      ],
      setAppPrivacyTwo: [
        '《隐私协议》',
        'https://petstore.deering.cn/html/privacy_policy.html'
      ],
      setSloganText: "登录后更精彩",
      setSloganOffsetY: 150,
      setSloganTextColor: '#ffffff',
      setSloganTextSize: 20,
      // setNavReturnHidden: true,
      setNavHidden: true,
      setWebNavColor: '#1d1d1d',
      setWebNavTextColor: '#FFFFFF',
      setAppPrivacyColor: ['#ffffff', '#ffffff'],
      setSwitchAccText: "使用手机号登录",
      // setPageBackgroundPath: "loding",
      // setPrivacyAlertIsNeedShow: true,
      // setPrivacyAlertWidth: 200,
      // setPrivacyAlertHeight: 200,
      // setPrivacyAlertAlignment: 1,
    );
    _aliyunNumberAuthPlugin.setUIConfig(aliyunNumberAuthUIConfig);
    _aliyunNumberAuthPlugin.quitLoginPage();

    ///唤起授权页面
    _aliyunNumberAuthPlugin.getLoginToken(1000);

    ///监听
    _aliyunNumberAuthPlugin.setUIClickListener((v) {
      if (v['code'] == '600000') {
        print(v);
        print('获取授权成功，执行登录操作');
        _aliyunNumberAuthPlugin.quitLoginPage();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
