package io.flutter.aliyun_number_auth.model

class AliyunNumberAuthConfig {
    var androidKey: String = ""
    var iosKey: String = ""
    var log: Boolean = false
    lateinit var aliyunNumberAuthUIConfig: AliyunNumberAuthUIConfig
}