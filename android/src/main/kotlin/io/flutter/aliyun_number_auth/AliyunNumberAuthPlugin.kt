package io.flutter.aliyun_number_auth

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import io.flutter.aliyun_number_auth.model.AliyunNumberAuthConfig

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.google.gson.Gson
import com.mobile.auth.gatewayauth.AuthUIConfig
import com.mobile.auth.gatewayauth.AuthUIControlClickListener
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.ResultCode
import com.mobile.auth.gatewayauth.TokenResultListener
import com.mobile.auth.gatewayauth.model.TokenRet
import io.flutter.aliyun_number_auth.model.AliyunNumberAuthUIConfig
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/** AliyunNumberAuthPlugin */
class AliyunNumberAuthPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var config: AliyunNumberAuthConfig
    private lateinit var uiConfig: AliyunNumberAuthUIConfig
    private lateinit var phoneNumberAuthHelper: PhoneNumberAuthHelper
    private lateinit var tokenResultListener: TokenResultListener
    private var activity: Activity? = null
    private lateinit var token: String
    private var isLoginAuth: Boolean = false

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "aliyun_number_auth")
        channel.setMethodCallHandler(this)
    }

    override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(p0: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "init") {
            //初始化
            //转对象
            val gson = Gson()
            val jsonString = gson.toJson(call.arguments)
            config = gson.fromJson(jsonString, AliyunNumberAuthConfig::class.java)
            if (config.iosKey == "" || config.androidKey == "") {
                result.error("600017", "密钥不能为空", null)
            } else {
                //初始化成功监听token
                tokenResultListener = object : TokenResultListener {
                    override fun onTokenSuccess(p0: String?) {
                        val tokenRet = TokenRet.fromJson(p0)
                        if (ResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.code)) {
                            val res = mapOf(
                                    "code" to tokenRet.code,
                                    "token" to tokenRet.token,
                                    "msg" to tokenRet.msg
                            )
                            if (config.log) {
                                channel.invokeMethod("onTokenSuccess", res)
                            }
                        } else if (ResultCode.CODE_SUCCESS.equals(tokenRet.code)) {
                            val res = mapOf(
                                    "code" to tokenRet.code,
                                    "token" to tokenRet.token,
                                    "msg" to tokenRet.msg
                            )
                            token = tokenRet.token
                            if (config.log) {
                                channel.invokeMethod("onTokenSuccess", res)
                            }
                        }
                    }

                    override fun onTokenFailed(p0: String?) {
                        result.error("error", "获取token失败：" + p0, null)
                    }
                }
                phoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance(activity, tokenResultListener)
                phoneNumberAuthHelper.setAuthListener(tokenResultListener)
                //设置SDK密钥
                phoneNumberAuthHelper.setAuthSDKInfo(config.androidKey)
                channel.invokeMethod("log", config)
                phoneNumberAuthHelper.setUIClickListener { s, context, s2 ->
                    val res = mapOf(
                            "code" to s,
                            "jsonString" to s2,
                    )
                    channel.invokeMethod("setAuthListener", res)
                }
                result.success(null)
            }
//
        } else if (call.method == "setUIConfig") {
            val authUIConfig = AuthUIConfig.Builder()
            //转对象
            val gson = Gson()
            val jsonString = gson.toJson(call.arguments)
            uiConfig = gson.fromJson(jsonString, AliyunNumberAuthUIConfig::class.java)
            // 导航栏
            if (uiConfig.setStatusBarColor != "") {
                authUIConfig.setStatusBarColor(Color.parseColor(uiConfig.setStatusBarColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setNavHidden: " + uiConfig.setStatusBarColor)
                }
            }
            if (uiConfig.setLightColor) {
                authUIConfig.setLightColor(true)
                if (config.log) {
                    channel.invokeMethod("log ", "setLightColor: " + uiConfig.setLightColor)
                }
            }
            if (uiConfig.setNavColor != "") {
                authUIConfig.setStatusBarColor(Color.parseColor(uiConfig.setNavColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setNavColor: " + uiConfig.setNavColor)
                }
            }
            if (uiConfig.setNavText != "") {
                authUIConfig.setNavText(uiConfig.setNavText)
                if (config.log) {
                    channel.invokeMethod("log ", "setNavText: " + uiConfig.setNavText)
                }
            }
            if (uiConfig.setNavTextColor != "") {
                authUIConfig.setNavTextColor(Color.parseColor(uiConfig.setNavTextColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setNavTextColor: " + uiConfig.setNavTextColor)
                }
            }
            if (uiConfig.setNavTextSize != 0) {
                authUIConfig.setNavTextSize(uiConfig.setNavTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setNavTextSize: " + uiConfig.setNavTextSize)
                }
            }
            if (uiConfig.setNavReturnHidden) {
                authUIConfig.setNavReturnHidden(uiConfig.setNavReturnHidden)
                if (config.log) {
                    channel.invokeMethod("log ", "setNavReturnHidden: " + uiConfig.setNavReturnHidden)
                }
            }
            if (uiConfig.setNavHidden) {
                authUIConfig.setNavHidden(uiConfig.setNavHidden)
                if (config.log) {
                    channel.invokeMethod("log ", "setNavHidden: " + uiConfig.setNavHidden)
                }
            }
            if (uiConfig.setStatusBarHidden) {
                authUIConfig.setStatusBarHidden(uiConfig.setStatusBarHidden)
                if (config.log) {
                    channel.invokeMethod("log ", "setStatusBarHidden: " + uiConfig.setStatusBarHidden)
                }
            }
            if (uiConfig.setStatusBarUIFlag != 0) {
                if (uiConfig.setStatusBarUIFlag == 1) {
                    authUIConfig.setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LOW_PROFILE)
                } else {
                    authUIConfig.setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                }
                if (config.log) {
                    channel.invokeMethod("log ", "setStatusBarUIFlag: " + uiConfig.setStatusBarUIFlag)
                }
            }
            if (uiConfig.setWebViewStatusBarColor != "") {
                authUIConfig.setNavTextColor(Color.RED)
                if (config.log) {
                    channel.invokeMethod("log ", "setWebViewStatusBarColor: " + uiConfig.setWebViewStatusBarColor)
                }
            }
            if (uiConfig.setWebNavColor != "") {
                authUIConfig.setWebNavColor(Color.parseColor(uiConfig.setWebNavColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setWebNavColor: " + Color.parseColor(uiConfig.setWebNavColor))
//                    channel.invokeMethod("log setWebNavColor", Color.WHITE)
                }
            }
            if (uiConfig.setWebNavTextColor != "") {
                authUIConfig.setWebNavTextColor(Color.parseColor(uiConfig.setWebNavTextColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setWebNavTextColor: " + uiConfig.setWebNavTextColor)
                }
            }
            if (uiConfig.setWebNavTextSize != 0) {
                authUIConfig.setWebNavTextSize(uiConfig.setWebNavTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setWebNavTextSize: " + uiConfig.setWebNavTextSize)
                }
            }
            if (uiConfig.setBottomNavColor != "") {
                authUIConfig.setBottomNavColor(Color.parseColor(uiConfig.setBottomNavColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setBottomNavColor: " + uiConfig.setBottomNavColor)
                }
            }
            if (uiConfig.setNavTextSizeDp != 0) {
                authUIConfig.setNavTextSizeDp(uiConfig.setNavTextSizeDp)
                if (config.log) {
                    channel.invokeMethod("log ", "setNavTextSizeDp: " + uiConfig.setNavTextSizeDp)
                }
            }
            // logo
            if (uiConfig.setLogoHidden) {
                authUIConfig.setLogoHidden(uiConfig.setLogoHidden)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoHidden: " + uiConfig.setLogoHidden)
                }
            }
            if (uiConfig.setLogoImgPath != "") {
                authUIConfig.setLogoImgPath(uiConfig.setLogoImgPath)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoImgPath: " + uiConfig.setLogoImgPath)
                }
            }
            if (uiConfig.setLogoWidth != 0) {
                authUIConfig.setLogoWidth(uiConfig.setLogoWidth)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoWidth: " + uiConfig.setLogoWidth)
                }
            }
            if (uiConfig.setLogoWidth != 0) {
                authUIConfig.setLogoWidth(uiConfig.setLogoWidth)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoWidth: " + uiConfig.setLogoWidth)
                }
            }
            if (uiConfig.setLogoHeight != 0) {
                authUIConfig.setLogoHeight(uiConfig.setLogoHeight)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoHeight: " + uiConfig.setLogoHeight)
                }
            }
            if (uiConfig.setLogoOffsetY != 0) {
                authUIConfig.setLogoWidth(uiConfig.setLogoOffsetY)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoOffsetY: " + uiConfig.setLogoOffsetY)
                }
            }
            if (uiConfig.setLogoOffsetY_B != 0) {
                authUIConfig.setLogoWidth(uiConfig.setLogoOffsetY_B)
                if (config.log) {
                    channel.invokeMethod("log", "setLogoOffsetY_B: " + uiConfig.setLogoOffsetY_B)
                }
            }
            if (uiConfig.setLogoScaleType != 0) {
                if (uiConfig.setLogoScaleType == 1) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.CENTER)
                } else if (uiConfig.setLogoScaleType == 2) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.CENTER_CROP)
                } else if (uiConfig.setLogoScaleType == 3) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.CENTER_INSIDE)
                } else if (uiConfig.setLogoScaleType == 4) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.MATRIX)
                } else if (uiConfig.setLogoScaleType == 5) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.FIT_CENTER)
                } else if (uiConfig.setLogoScaleType == 6) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.FIT_END)
                } else if (uiConfig.setLogoScaleType == 7) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.FIT_CENTER)
                } else if (uiConfig.setLogoScaleType == 8) {
                    authUIConfig.setLogoScaleType(ImageView.ScaleType.FIT_XY)
                }

                if (config.log) {
                    channel.invokeMethod("log", "setLogoScaleType: " + uiConfig.setLogoScaleType)
                }
            }
            // slogan
            if (uiConfig.setSloganText != "") {
                authUIConfig.setSloganText(uiConfig.setSloganText)
                if (config.log) {
                    channel.invokeMethod("log ", "setSloganText: " + uiConfig.setSloganText)
                }
            }
            if (uiConfig.setSloganTextColor != "") {
                authUIConfig.setSloganTextColor(Color.parseColor(uiConfig.setSloganTextColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setSloganTextColor: " + uiConfig.setSloganTextColor)
                }
            }
            if (uiConfig.setSloganTextSize != 0) {
                authUIConfig.setSloganTextSize(uiConfig.setSloganTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setSloganTextSize: " + uiConfig.setSloganTextSize)
                }
            }
            if (uiConfig.setSloganOffsetY != 0) {
                authUIConfig.setSloganOffsetY(uiConfig.setSloganOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setSloganOffsetY: " + uiConfig.setSloganOffsetY)
                }
            }
            if (uiConfig.setSloganOffsetY_B != 0) {
                authUIConfig.setSloganOffsetY_B(uiConfig.setSloganOffsetY_B)
                if (config.log) {
                    channel.invokeMethod("log ", "setSloganOffsetY_B: " + uiConfig.setSloganTextSize)
                }
            }
            if (uiConfig.setSloganTextSizeDp != 0) {
                authUIConfig.setSloganTextSizeDp(uiConfig.setSloganTextSizeDp)
                if (config.log) {
                    channel.invokeMethod("log ", "setSloganTextSizeDp: " + uiConfig.setSloganTextSizeDp)
                }
            }
            // 授权页号码栏
            if (uiConfig.setNumberColor != "") {
                authUIConfig.setNumberColor(Color.parseColor(uiConfig.setNumberColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberColor: " + uiConfig.setNumberColor)
                }
            }
            if (uiConfig.setNumberSize != 0) {
                authUIConfig.setNumberSize(uiConfig.setNumberSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberSize: " + uiConfig.setNumberSize)
                }
            }
            if (uiConfig.setNumFieldOffsetY != 0) {
                authUIConfig.setNumFieldOffsetY(uiConfig.setNumFieldOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumFieldOffsetY: " + uiConfig.setNumFieldOffsetY)
                }
            }
            if (uiConfig.setNumFieldOffsetY_B != 0) {
                authUIConfig.setNumFieldOffsetY(uiConfig.setNumFieldOffsetY_B)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumFieldOffsetY_B: " + uiConfig.setNumFieldOffsetY_B)
                }
            }
            if (uiConfig.setNumberFieldOffsetX != 0) {
                authUIConfig.setNumFieldOffsetY(uiConfig.setNumberFieldOffsetX)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberFieldOffsetX: " + uiConfig.setNumberFieldOffsetX)
                }
            }
            if (uiConfig.setNumberLayoutGravity != 0) {
                authUIConfig.setNumFieldOffsetY(uiConfig.setNumberLayoutGravity)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberLayoutGravity: " + uiConfig.setNumberLayoutGravity)
                }
            }
            if (uiConfig.setNumberSizeDp != 0) {
                authUIConfig.setNumFieldOffsetY(uiConfig.setNumberSizeDp)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberSizeDp: " + uiConfig.setNumberSizeDp)
                }
            }
            if (uiConfig.useNumberFontAndPath.count() == 2) {
                authUIConfig.useNumberFontAndPath(uiConfig.useNumberFontAndPath[0].toBoolean(), uiConfig.useNumberFontAndPath[1])
                if (config.log) {
                    channel.invokeMethod("log ", "useNumberFontAndPath: " + uiConfig.useNumberFontAndPath[0] + "-" + uiConfig.useNumberFontAndPath[1])
                }
            }
            if (uiConfig.setNumberTypeface != 0) {
                if (uiConfig.setNumberTypeface == 1) {
                    authUIConfig.setNumberTypeface(Typeface.SANS_SERIF)
                } else if (uiConfig.setNumberTypeface == 2) {
                    authUIConfig.setNumberTypeface(Typeface.SERIF)
                } else if (uiConfig.setNumberTypeface == 3) {
                    authUIConfig.setNumberTypeface(Typeface.MONOSPACE)
                }
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberTypeface: " + uiConfig.setNumberTypeface)
                }
            }
            if (uiConfig.setNumberTextSpace != 0F) {
                authUIConfig.setNumberTextSpace(uiConfig.setNumberTextSpace)
                if (config.log) {
                    channel.invokeMethod("log ", "setNumberTextSpace: " + uiConfig.setNumberTextSpace)
                }
            }
            // 授权页登录按钮
            if (uiConfig.setLogBtnText != "") {
                authUIConfig.setLogBtnText(uiConfig.setLogBtnText)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnText: " + uiConfig.setLogBtnText)
                }
            }
            if (uiConfig.setLogBtnTextColor != "") {
                authUIConfig.setLogBtnTextColor(Color.parseColor(uiConfig.setLogBtnTextColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnTextColor: " + uiConfig.setLogBtnTextColor)
                }
            }
            if (uiConfig.setLogBtnTextSize != 0) {
                authUIConfig.setLogBtnTextSize(uiConfig.setLogBtnTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnTextSize: " + uiConfig.setLogBtnTextSize)
                }
            }
            if (uiConfig.setLogBtnWidth != 0) {
                authUIConfig.setLogBtnWidth(uiConfig.setLogBtnWidth)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnWidth: " + uiConfig.setLogBtnWidth)
                }
            }
            if (uiConfig.setLogBtnHeight != 0) {
                authUIConfig.setLogBtnWidth(uiConfig.setLogBtnHeight)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnHeight: " + uiConfig.setLogBtnHeight)
                }
            }
            if (uiConfig.setLogBtnMarginLeftAndRight != 0) {
                authUIConfig.setLogBtnMarginLeftAndRight(uiConfig.setLogBtnMarginLeftAndRight)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnMarginLeftAndRight: " + uiConfig.setLogBtnMarginLeftAndRight)
                }
            }
            if (uiConfig.setLogBtnBackgroundPath != "") {
                authUIConfig.setLogBtnBackgroundPath(uiConfig.setLogBtnBackgroundPath)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnBackgroundPath: " + uiConfig.setLogBtnBackgroundPath)
                }
            }
            if (uiConfig.setLogBtnOffsetY != 0) {
                authUIConfig.setLogBtnOffsetY(uiConfig.setLogBtnOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnOffsetY: " + uiConfig.setLogBtnOffsetY)
                }
            }
            if (uiConfig.setLogBtnOffsetY_B != 0) {
                authUIConfig.setLogBtnOffsetY_B(uiConfig.setLogBtnOffsetY_B)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnOffsetY_B: " + uiConfig.setLogBtnOffsetY_B)
                }
            }
            if (uiConfig.setLoadingImgPath != "") {
                authUIConfig.setLoadingImgPath(uiConfig.setLoadingImgPath)
                if (config.log) {
                    channel.invokeMethod("log ", "setLoadingImgPath: " + uiConfig.setLoadingImgPath)
                }
            }
            if (uiConfig.setLogBtnOffsetX != 0) {
                authUIConfig.setLogBtnOffsetX(uiConfig.setLogBtnOffsetX)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnOffsetX: " + uiConfig.setLogBtnOffsetX)
                }
            }
            if (uiConfig.setLogBtnLayoutGravity != 0) {
                if (uiConfig.setLogBtnLayoutGravity == 1) {
                    authUIConfig.setLogBtnLayoutGravity(Gravity.CLIP_HORIZONTAL)
                } else if (uiConfig.setLogBtnLayoutGravity == 2) {
                    authUIConfig.setLogBtnLayoutGravity(Gravity.LEFT)
                } else if (uiConfig.setLogBtnLayoutGravity == 3) {
                    authUIConfig.setLogBtnLayoutGravity(Gravity.RIGHT)
                }
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnLayoutGravity: " + uiConfig.setLogBtnLayoutGravity)
                }
            }
            if (uiConfig.setLogBtnTextSizeDp != 0) {
                authUIConfig.setLogBtnTextSizeDp(uiConfig.setLogBtnTextSizeDp)
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnTextSizeDp: " + uiConfig.setLogBtnTextSizeDp)
                }
            }
            if (uiConfig.setLogBtnBackgroundDrawable != "") {
                authUIConfig.setLogBtnBackgroundDrawable(Drawable.createFromPath(uiConfig.setLogBtnBackgroundDrawable))
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnBackgroundDrawable: " + uiConfig.setLogBtnBackgroundDrawable)
                }
            }
            if (uiConfig.setLoadingImgDrawable != "") {
                authUIConfig.setLoadingImgDrawable(Drawable.createFromPath(uiConfig.setLoadingImgDrawable))
                if (config.log) {
                    channel.invokeMethod("log ", "setLoadingImgDrawable: " + uiConfig.setLoadingImgDrawable)
                }
            }
            if (uiConfig.useLogBtnFontAndPath.count() == 2) {
                authUIConfig.useNumberFontAndPath(uiConfig.useLogBtnFontAndPath[0].toBoolean(), uiConfig.useLogBtnFontAndPath[1])
                if (config.log) {
                    channel.invokeMethod("log ", "useLogBtnFontAndPath: " + uiConfig.useLogBtnFontAndPath[0] + "-" + uiConfig.useLogBtnFontAndPath[1])
                }
            }
            if (uiConfig.setLogBtnTypeface != 0) {
                if (uiConfig.setLogBtnTypeface == 1) {
                    authUIConfig.setLogBtnTypeface(Typeface.SANS_SERIF)
                } else if (uiConfig.setLogBtnTypeface == 2) {
                    authUIConfig.setLogBtnTypeface(Typeface.SERIF)
                } else if (uiConfig.setLogBtnTypeface == 3) {
                    authUIConfig.setLogBtnTypeface(Typeface.MONOSPACE)
                }
                if (config.log) {
                    channel.invokeMethod("log ", "setLogBtnTypeface: " + uiConfig.setLogBtnTypeface)
                }
            }
            // 隐私协议
            if (uiConfig.setAppPrivacyOne.count() == 2) {
                authUIConfig.setAppPrivacyOne(uiConfig.setAppPrivacyOne[0], uiConfig.setAppPrivacyOne[1])
                if (config.log) {
                    channel.invokeMethod("log ", "setAppPrivacyOne: " + uiConfig.setAppPrivacyOne[0])
                }
            }
            if (uiConfig.setAppPrivacyTwo.count() == 2) {
                authUIConfig.setAppPrivacyTwo(uiConfig.setAppPrivacyTwo[0], uiConfig.setAppPrivacyTwo[1])
                if (config.log) {
                    channel.invokeMethod("log ", "setAppPrivacyTwo: " + uiConfig.setAppPrivacyTwo[0])
                }
            }
            if (uiConfig.setAppPrivacyThree.count() == 2) {
                authUIConfig.setAppPrivacyThree(uiConfig.setAppPrivacyThree[0], uiConfig.setAppPrivacyThree[1])
                if (config.log) {
                    channel.invokeMethod("log ", "setAppPrivacyThree: " + uiConfig.setAppPrivacyThree[0])
                }
            }
            if (uiConfig.setAppPrivacyColor.count() == 2) {
                authUIConfig.setAppPrivacyColor(Color.parseColor(uiConfig.setAppPrivacyColor[0]), Color.parseColor(uiConfig.setAppPrivacyColor[1]))
                if (config.log) {
                    channel.invokeMethod("log ", "setAppPrivacyThree: " + uiConfig.setAppPrivacyColor[0] + "-" + uiConfig.setAppPrivacyColor[1])
                }
            }
            if (uiConfig.setPrivacyOffsetY != 0) {
                authUIConfig.setPrivacyOffsetY(uiConfig.setPrivacyOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyOffsetY: " + uiConfig.setPrivacyOffsetY)
                }
            }
            if (uiConfig.setPrivacyOffsetY_B != 0) {
                authUIConfig.setPrivacyOffsetY_B(uiConfig.setPrivacyOffsetY_B)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyOffsetY_B: " + uiConfig.setPrivacyOffsetY_B)
                }
            }
            if (uiConfig.setPrivacyState) {
                authUIConfig.setPrivacyState(uiConfig.setPrivacyState)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyState: " + uiConfig.setPrivacyState)
                }
            }
            if (uiConfig.setPrivacyTextSize != 0) {
                authUIConfig.setPrivacyTextSize(uiConfig.setPrivacyTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyTextSize: " + uiConfig.setPrivacyTextSize)
                }
            }
            if (uiConfig.setVendorPrivacyPrefix != "") {
                authUIConfig.setVendorPrivacyPrefix(uiConfig.setVendorPrivacyPrefix)
                if (config.log) {
                    channel.invokeMethod("log ", "setVendorPrivacyPrefix: " + uiConfig.setVendorPrivacyPrefix)
                }
            }
            if (uiConfig.setVendorPrivacySuffix != "") {
                authUIConfig.setVendorPrivacyPrefix(uiConfig.setVendorPrivacySuffix)
                if (config.log) {
                    channel.invokeMethod("log ", "setVendorPrivacySuffix: " + uiConfig.setVendorPrivacySuffix)
                }
            }
            if (uiConfig.setPrivacyOneColor != "") {
                authUIConfig.setPrivacyOneColor(Color.parseColor(uiConfig.setPrivacyOneColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyOneColor: " + uiConfig.setPrivacyOneColor)
                }
            }
            if (uiConfig.setPrivacyTwoColor != "") {
                authUIConfig.setPrivacyTwoColor(Color.parseColor(uiConfig.setPrivacyTwoColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyTwoColor: " + uiConfig.setPrivacyTwoColor)
                }
            }
            if (uiConfig.setPrivacyThreeColor != "") {
                authUIConfig.setPrivacyThreeColor(Color.parseColor(uiConfig.setPrivacyThreeColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyThreeColor: " + uiConfig.setPrivacyThreeColor)
                }
            }
            if (uiConfig.setPrivacyOperatorColor != "") {
                authUIConfig.setPrivacyOperatorColor(Color.parseColor(uiConfig.setPrivacyOperatorColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyOperatorColor: " + uiConfig.setPrivacyOperatorColor)
                }
            }
            if (uiConfig.setCheckBoxWidth != 0) {
                authUIConfig.setCheckBoxWidth(uiConfig.setCheckBoxWidth)
                if (config.log) {
                    channel.invokeMethod("log ", "setCheckBoxWidth: " + uiConfig.setCheckBoxWidth)
                }
            }
            if (uiConfig.setCheckBoxHeight != 0) {
                authUIConfig.setCheckBoxHeight(uiConfig.setCheckBoxHeight)
                if (config.log) {
                    channel.invokeMethod("log ", "setCheckBoxHeight: " + uiConfig.setCheckBoxHeight)
                }
            }

            // 切换控件
            if (uiConfig.setSwitchAccHidden) {
                authUIConfig.setSwitchAccHidden(uiConfig.setSwitchAccHidden)
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchAccHidden: " + uiConfig.setSwitchAccHidden)
                }
            }
            if (uiConfig.setSwitchAccText != "") {
                authUIConfig.setSwitchAccText(uiConfig.setSwitchAccText)
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchAccText: " + uiConfig.setSwitchAccText)
                }
            }
            if (uiConfig.setSwitchAccTextColor != "") {
                authUIConfig.setSwitchAccTextColor(Color.parseColor(uiConfig.setSwitchAccTextColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchAccTextColor: " + uiConfig.setSwitchAccTextColor)
                }
            }
            if (uiConfig.setSwitchAccTextSize != 0) {
                authUIConfig.setSwitchAccTextSize(uiConfig.setSwitchAccTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchAccTextSize: " + uiConfig.setSwitchAccTextSize)
                }
            }
            if (uiConfig.setSwitchOffsetY != 0) {
                authUIConfig.setSwitchOffsetY(uiConfig.setSwitchOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchOffsetY: " + uiConfig.setSwitchOffsetY)
                }
            }
            if (uiConfig.setSwitchOffsetY_B != 0) {
                authUIConfig.setSwitchOffsetY_B(uiConfig.setSwitchOffsetY_B)
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchOffsetY_B: " + uiConfig.setSwitchOffsetY_B)
                }
            }
            if (uiConfig.setSwitchAccTextSizeDp != 0) {
                authUIConfig.setSwitchOffsetY(uiConfig.setSwitchAccTextSizeDp)
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchAccTextSizeDp: " + uiConfig.setSwitchAccTextSizeDp)
                }
            }
            if (uiConfig.useSwitchFontAndPath.count() == 2) {
                authUIConfig.useSwitchFontAndPath(uiConfig.useSwitchFontAndPath[0].toBoolean(), uiConfig.useSwitchFontAndPath[1])
                if (config.log) {
                    channel.invokeMethod("log ", "useSwitchFontAndPath: " + uiConfig.useSwitchFontAndPath[0] + "-" + uiConfig.useSwitchFontAndPath[1])
                }
            }
            if (uiConfig.setSwitchTypeface != 0) {
                if (uiConfig.setSwitchTypeface == 1) {
                    authUIConfig.setSwitchTypeface(Typeface.SANS_SERIF)
                } else if (uiConfig.setSwitchTypeface == 2) {
                    authUIConfig.setSwitchTypeface(Typeface.SERIF)
                } else if (uiConfig.setSwitchTypeface == 3) {
                    authUIConfig.setSwitchTypeface(Typeface.MONOSPACE)
                }
                if (config.log) {
                    channel.invokeMethod("log ", "setSwitchTypeface: " + uiConfig.setSwitchTypeface)
                }
            }

            // 授权页
            if (uiConfig.setAuthPageActIn.count() == 2) {
                authUIConfig.setAuthPageActIn(uiConfig.setAuthPageActIn[0], uiConfig.setAuthPageActIn[1])
                if (config.log) {
                    channel.invokeMethod("log ", "setAuthPageActIn: " + uiConfig.setAuthPageActIn[0] + "-" + uiConfig.setAuthPageActIn[1])
                }
            }
            if (uiConfig.setAuthPageActOut.count() == 2) {
                authUIConfig.setAuthPageActOut(uiConfig.setAuthPageActOut[0], uiConfig.setAuthPageActOut[1])
                if (config.log) {
                    channel.invokeMethod("log ", "setAuthPageActOut: " + uiConfig.setAuthPageActOut[0] + "-" + uiConfig.setAuthPageActOut[1])
                }
            }
            if (uiConfig.setPageBackgroundPath != "") {
                authUIConfig.setPageBackgroundPath(uiConfig.setPageBackgroundPath)
                if (config.log) {
                    channel.invokeMethod("log ", "setPageBackgroundPath: " + uiConfig.setPageBackgroundPath)
                }
            }
            if (uiConfig.setDialogWidth != 0) {
                authUIConfig.setDialogWidth(uiConfig.setDialogWidth)
                if (config.log) {
                    channel.invokeMethod("log ", "setDialogWidth: " + uiConfig.setDialogWidth)
                }
            }
            if (uiConfig.setDialogHeight != 0) {
                authUIConfig.setDialogHeight(uiConfig.setDialogHeight)
                if (config.log) {
                    channel.invokeMethod("log ", "setDialogHeight: " + uiConfig.setDialogHeight)
                }
            }
            if (uiConfig.setDialogAlpha != 0F) {
                authUIConfig.setDialogAlpha(uiConfig.setDialogAlpha)
                if (config.log) {
                    channel.invokeMethod("log ", "setDialogAlpha: " + uiConfig.setDialogAlpha)
                }
            }
            if (uiConfig.setDialogOffsetX != 0) {
                authUIConfig.setDialogOffsetX(uiConfig.setDialogOffsetX)
                if (config.log) {
                    channel.invokeMethod("log ", "setDialogOffsetX: " + uiConfig.setDialogOffsetX)
                }
            }
            if (uiConfig.setDialogoffsetY != 0) {
                authUIConfig.setDialogOffsetY(uiConfig.setDialogoffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setDialogoffsetY: " + uiConfig.setDialogoffsetY)
                }
            }
            if (uiConfig.setTapAuthPageMaskClosePage) {
                authUIConfig.setTapAuthPageMaskClosePage(uiConfig.setTapAuthPageMaskClosePage)
                if (config.log) {
                    channel.invokeMethod("log ", "setTapAuthPageMaskClosePage: " + uiConfig.setTapAuthPageMaskClosePage)
                }
            }
            if (uiConfig.setDialogBottom) {
                authUIConfig.setTapAuthPageMaskClosePage(uiConfig.setDialogBottom)
                if (config.log) {
                    channel.invokeMethod("log ", "setDialogBottom: " + uiConfig.setDialogBottom)
                }
            }
            if (uiConfig.setProtocolAction != "") {
                authUIConfig.setProtocolAction(uiConfig.setProtocolAction)
                if (config.log) {
                    channel.invokeMethod("log ", "setProtocolAction: " + uiConfig.setProtocolAction)
                }
            }
            if (uiConfig.setPackageName != "") {
                authUIConfig.setPackageName(uiConfig.setPackageName)
                if (config.log) {
                    channel.invokeMethod("log ", "setPackageName: " + uiConfig.setPackageName)
                }
            }
            if (uiConfig.setWebCacheMode != 0) {
                authUIConfig.setWebCacheMode(uiConfig.setWebCacheMode)
                if (config.log) {
                    channel.invokeMethod("log ", "setWebCacheMode: " + uiConfig.setWebCacheMode)
                }
            }
            // 二次弹窗
            if (uiConfig.setPrivacyAlertIsNeedShow) {
                authUIConfig.setPrivacyAlertIsNeedShow(uiConfig.setPrivacyAlertIsNeedShow)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertIsNeedShow: " + uiConfig.setPrivacyAlertIsNeedShow)
                }
            }
            if (uiConfig.setPrivacyAlertIsNeedAutoLogin) {
                authUIConfig.setPrivacyAlertIsNeedAutoLogin(uiConfig.setPrivacyAlertIsNeedAutoLogin)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertIsNeedAutoLogin: " + uiConfig.setPrivacyAlertIsNeedAutoLogin)
                }
            }
            if (uiConfig.setPrivacyAlertMaskIsNeedShow) {
                authUIConfig.setPrivacyAlertMaskIsNeedShow(uiConfig.setPrivacyAlertMaskIsNeedShow)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertMaskIsNeedShow: " + uiConfig.setPrivacyAlertMaskIsNeedShow)
                }
            }
            if (uiConfig.setPrivacyAlertMaskAlpha != 0F) {
                authUIConfig.setPrivacyAlertMaskAlpha(uiConfig.setPrivacyAlertMaskAlpha)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertMaskAlpha: " + uiConfig.setPrivacyAlertMaskAlpha)
                }
            }
            if (uiConfig.setPrivacyAlertMaskAlpha != 0F) {
                authUIConfig.setPrivacyAlertMaskAlpha(uiConfig.setPrivacyAlertMaskAlpha)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertMaskAlpha: " + uiConfig.setPrivacyAlertMaskAlpha)
                }
            }
            if (uiConfig.setPrivacyAlertAlpha != 0F) {
                authUIConfig.setPrivacyAlertAlpha(uiConfig.setPrivacyAlertAlpha)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertAlpha: " + uiConfig.setPrivacyAlertAlpha)
                }
            }
            if (uiConfig.setPrivacyAlertBackgroundColor != "") {
                authUIConfig.setPrivacyAlertBackgroundColor(Color.parseColor(uiConfig.setPrivacyAlertBackgroundColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBackgroundColor: " + uiConfig.setPrivacyAlertBackgroundColor)
                }
            }
            if (uiConfig.setPrivacyAlertEntryAnimation != "") {
                authUIConfig.setPrivacyAlertEntryAnimation(uiConfig.setPrivacyAlertEntryAnimation)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertEntryAnimation: " + uiConfig.setPrivacyAlertEntryAnimation)
                }
            }
            if (uiConfig.setPrivacyAlertExitAnimation != "") {
                authUIConfig.setPrivacyAlertExitAnimation(uiConfig.setPrivacyAlertExitAnimation)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertExitAnimation: " + uiConfig.setPrivacyAlertExitAnimation)
                }
            }
            if (uiConfig.setPrivacyAlertCornerRadiusArray.count() == 4) {
                authUIConfig.setPrivacyAlertCornerRadiusArray(uiConfig.setPrivacyAlertCornerRadiusArray.toIntArray())
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertCornerRadiusArray: " + uiConfig.setPrivacyAlertCornerRadiusArray)
                }
            }
            if (uiConfig.setPrivacyAlertMaskAlpha != 0F) {
                authUIConfig.setPrivacyAlertMaskAlpha(uiConfig.setPrivacyAlertMaskAlpha)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertMaskAlpha: " + uiConfig.setPrivacyAlertMaskAlpha)
                }
            }
            if (uiConfig.setPrivacyAlertAlpha != 0F) {
                authUIConfig.setPrivacyAlertAlpha(uiConfig.setPrivacyAlertAlpha)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertAlpha: " + uiConfig.setPrivacyAlertAlpha)
                }
            }
            if (uiConfig.setPrivacyAlertBackgroundColor != "") {
                authUIConfig.setPrivacyAlertBackgroundColor(Color.parseColor(uiConfig.setPrivacyAlertBackgroundColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBackgroundColor: " + uiConfig.setPrivacyAlertBackgroundColor)
                }
            }
            if (uiConfig.setPrivacyAlertEntryAnimation != "") {
                authUIConfig.setPrivacyAlertEntryAnimation(uiConfig.setPrivacyAlertEntryAnimation)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertEntryAnimation: " + uiConfig.setPrivacyAlertEntryAnimation)
                }
            }
            if (uiConfig.setPrivacyAlertExitAnimation != "") {
                authUIConfig.setPrivacyAlertExitAnimation(uiConfig.setPrivacyAlertExitAnimation)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertExitAnimation: " + uiConfig.setPrivacyAlertExitAnimation)
                }
            }
            if (uiConfig.setPrivacyAlertCornerRadiusArray.count() == 4) {
                authUIConfig.setPrivacyAlertCornerRadiusArray(uiConfig.setPrivacyAlertCornerRadiusArray.toIntArray())
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertCornerRadiusArray: " + uiConfig.setPrivacyAlertCornerRadiusArray)
                }
            }
            if (uiConfig.setPrivacyAlertAlignment != 0) {
                authUIConfig.setPrivacyAlertAlignment(uiConfig.setPrivacyAlertAlignment)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertAlignment: " + uiConfig.setPrivacyAlertAlignment)
                }
            }
            if (uiConfig.setPrivacyAlertWidth != 0) {
                authUIConfig.setPrivacyAlertWidth(uiConfig.setPrivacyAlertWidth)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertWidth: " + uiConfig.setPrivacyAlertWidth)
                }
            }
            if (uiConfig.setPrivacyAlertHeight != 0) {
                authUIConfig.setPrivacyAlertHeight(uiConfig.setPrivacyAlertHeight)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertHeight: " + uiConfig.setPrivacyAlertHeight)
                }
            }
            if (uiConfig.setPrivacyAlertOffsetX != 0) {
                authUIConfig.setPrivacyAlertOffsetX(uiConfig.setPrivacyAlertOffsetX)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertOffsetX: " + uiConfig.setPrivacyAlertOffsetX)
                }
            }
            if (uiConfig.setPrivacyAlertOffsetY != 0) {
                authUIConfig.setPrivacyAlertOffsetY(uiConfig.setPrivacyAlertOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertOffsetY: " + uiConfig.setPrivacyAlertOffsetY)
                }
            }
            if (uiConfig.setPrivacyAlertTitleBackgroundColor != "") {
                authUIConfig.setPrivacyAlertTitleBackgroundColor(Color.parseColor(uiConfig.setPrivacyAlertTitleBackgroundColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleBackgroundColor: " + uiConfig.setPrivacyAlertTitleBackgroundColor)
                }
            }
            if (uiConfig.setPrivacyAlertTitleAlignment != 0) {
                authUIConfig.setPrivacyAlertTitleAlignment(uiConfig.setPrivacyAlertTitleAlignment)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleAlignment: " + uiConfig.setPrivacyAlertTitleAlignment)
                }
            }
            if (uiConfig.setPrivacyAlertTitleOffsetX != 0) {
                authUIConfig.setPrivacyAlertTitleOffsetX(uiConfig.setPrivacyAlertTitleOffsetX)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleOffsetX: " + uiConfig.setPrivacyAlertTitleOffsetX)
                }
            }
            if (uiConfig.setPrivacyAlertTitleOffsetY != 0) {
                authUIConfig.setPrivacyAlertTitleOffsetY(uiConfig.setPrivacyAlertTitleOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleOffsetY: " + uiConfig.setPrivacyAlertTitleOffsetY)
                }
            }
            if (uiConfig.setPrivacyAlertTitleContent != "") {
                authUIConfig.setPrivacyAlertTitleContent(uiConfig.setPrivacyAlertTitleContent)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleContent: " + uiConfig.setPrivacyAlertTitleContent)
                }
            }
            if (uiConfig.setPrivacyAlertTitleTextSize != 0) {
                authUIConfig.setPrivacyAlertTitleTextSize(uiConfig.setPrivacyAlertTitleTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleTextSize: " + uiConfig.setPrivacyAlertTitleTextSize)
                }
            }
            if (uiConfig.setPrivacyAlertTitleColor != "") {
                authUIConfig.setPrivacyAlertTitleColor(Color.parseColor(uiConfig.setPrivacyAlertTitleColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTitleColor: " + uiConfig.setPrivacyAlertTitleColor)
                }
            }
            if (uiConfig.setPrivacyAlertContentBackgroundColor != "") {
                authUIConfig.setPrivacyAlertContentBackgroundColor(Color.parseColor(uiConfig.setPrivacyAlertContentBackgroundColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentBackgroundColor: " + uiConfig.setPrivacyAlertContentBackgroundColor)
                }
            }
            if (uiConfig.setPrivacyAlertContentTextSize != 0) {
                authUIConfig.setPrivacyAlertContentTextSize(uiConfig.setPrivacyAlertContentTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentTextSize: " + uiConfig.setPrivacyAlertContentTextSize)
                }
            }
            if (uiConfig.setPrivacyAlertContentAlignment != 0) {
                authUIConfig.setPrivacyAlertContentAlignment(uiConfig.setPrivacyAlertContentAlignment)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentAlignment: " + uiConfig.setPrivacyAlertContentAlignment)
                }
            }
            if (uiConfig.setPrivacyAlertContentColor != "") {
                authUIConfig.setPrivacyAlertContentColor(Color.parseColor(uiConfig.setPrivacyAlertContentColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentColor: " + uiConfig.setPrivacyAlertContentColor)
                }
            }
            if (uiConfig.setPrivacyAlertContentBaseColor != "") {
                authUIConfig.setPrivacyAlertContentBaseColor(Color.parseColor(uiConfig.setPrivacyAlertContentBaseColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentBaseColor: " + uiConfig.setPrivacyAlertContentBaseColor)
                }
            }
            if (uiConfig.setPrivacyAlertContentHorizontalMargin != 0) {
                authUIConfig.setPrivacyAlertContentHorizontalMargin(uiConfig.setPrivacyAlertContentHorizontalMargin)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentHorizontalMargin: " + uiConfig.setPrivacyAlertContentHorizontalMargin)
                }
            }
            if (uiConfig.setPrivacyAlertContentVerticalMargin != 0) {
                authUIConfig.setPrivacyAlertContentVerticalMargin(uiConfig.setPrivacyAlertContentVerticalMargin)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertContentVerticalMargin: " + uiConfig.setPrivacyAlertContentVerticalMargin)
                }
            }
            if (uiConfig.setPrivacyAlertBtnBackgroundImgPath != "") {
                authUIConfig.setPrivacyAlertBtnBackgroundImgPath(uiConfig.setPrivacyAlertBtnBackgroundImgPath)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnBackgroundImgPath: " + uiConfig.setPrivacyAlertBtnBackgroundImgPath)
                }
            }
            if (uiConfig.setPrivacyAlertBtnTextColor != "") {
                authUIConfig.setPrivacyAlertBtnTextColor(Color.parseColor(uiConfig.setPrivacyAlertBtnTextColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnTextColor: " + uiConfig.setPrivacyAlertBtnTextColor)
                }
            }
            if (uiConfig.setPrivacyAlertBtnTextSize != 0) {
                authUIConfig.setPrivacyAlertBtnTextSize(uiConfig.setPrivacyAlertBtnTextSize)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnTextSize: " + uiConfig.setPrivacyAlertBtnTextSize)
                }
            }
            if (uiConfig.setPrivacyAlertBtnWidth != 0) {
                authUIConfig.setPrivacyAlertBtnWidth(uiConfig.setPrivacyAlertBtnWidth)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnWidth: " + uiConfig.setPrivacyAlertBtnWidth)
                }
            }
            if (uiConfig.setPrivacyAlertBtnHeigth != 0) {
                authUIConfig.setPrivacyAlertBtnHeigth(uiConfig.setPrivacyAlertBtnHeigth)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnHeigth: " + uiConfig.setPrivacyAlertBtnHeigth)
                }
            }
            if (uiConfig.setPrivacyAlertCloseBtnShow) {
                authUIConfig.setPrivacyAlertCloseBtnShow(uiConfig.setPrivacyAlertCloseBtnShow)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertCloseBtnShow: " + uiConfig.setPrivacyAlertCloseBtnShow)
                }
            }
            if (uiConfig.setPrivacyAlertCloseImagPath != "") {
                authUIConfig.setPrivacyAlertCloseImagPath(uiConfig.setPrivacyAlertCloseImagPath)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertCloseImagPath: " + uiConfig.setPrivacyAlertCloseImagPath)
                }
            }
            if (uiConfig.setPrivacyAlertCloseImgWidth != 0) {
                authUIConfig.setPrivacyAlertCloseImgWidth(uiConfig.setPrivacyAlertCloseImgWidth)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertCloseImgWidth: " + uiConfig.setPrivacyAlertCloseImgWidth)
                }
            }
            if (uiConfig.setPrivacyAlertCloseImgHeight != 0) {
                authUIConfig.setPrivacyAlertCloseImgHeight(uiConfig.setPrivacyAlertCloseImgHeight)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertCloseImgHeight: " + uiConfig.setPrivacyAlertCloseImgHeight)
                }
            }
            if (uiConfig.setPrivacyAlertBtnContent != "") {
                authUIConfig.setPrivacyAlertBtnContent(uiConfig.setPrivacyAlertBtnContent)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnContent: " + uiConfig.setPrivacyAlertBtnContent)
                }
            }
            if (uiConfig.setPrivacyAlertBtnOffsetX != 0) {
                authUIConfig.setPrivacyAlertBtnOffsetX(uiConfig.setPrivacyAlertBtnOffsetX)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnOffsetX: " + uiConfig.setPrivacyAlertBtnOffsetX)
                }
            }
            if (uiConfig.setPrivacyAlertBtnOffsetY != 0) {
                authUIConfig.setPrivacyAlertBtnOffsetY(uiConfig.setPrivacyAlertBtnOffsetY)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnOffsetY: " + uiConfig.setPrivacyAlertBtnOffsetY)
                }
            }
            if (uiConfig.setPrivacyAlertBtnHorizontalMargin != 0) {
                authUIConfig.setPrivacyAlertBtnHorizontalMargin(uiConfig.setPrivacyAlertBtnHorizontalMargin)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnHorizontalMargin: " + uiConfig.setPrivacyAlertBtnHorizontalMargin)
                }
            }
            if (uiConfig.setPrivacyAlertBtnVerticalMargin != 0) {
                authUIConfig.setPrivacyAlertBtnVerticalMargin(uiConfig.setPrivacyAlertBtnVerticalMargin)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBtnVerticalMargin: " + uiConfig.setPrivacyAlertBtnVerticalMargin)
                }
            }
            if (uiConfig.setTapPrivacyAlertMaskCloseAlert) {
                authUIConfig.setTapPrivacyAlertMaskCloseAlert(uiConfig.setTapPrivacyAlertMaskCloseAlert)
                if (config.log) {
                    channel.invokeMethod("log ", "setTapPrivacyAlertMaskCloseAlert: " + uiConfig.setTapPrivacyAlertMaskCloseAlert)
                }
            }
            if (uiConfig.setPrivacyAlertBefore != "") {
                authUIConfig.setPrivacyAlertBefore(uiConfig.setPrivacyAlertBefore)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertBefore: " + uiConfig.setPrivacyAlertBefore)
                }
            }
            if (uiConfig.setPrivacyAlertEnd != "") {
                authUIConfig.setPrivacyAlertEnd(uiConfig.setPrivacyAlertEnd)
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertEnd: " + uiConfig.setPrivacyAlertEnd)
                }
            }
            if (uiConfig.setPrivacyAlertOneColor != "") {
                authUIConfig.setPrivacyAlertOneColor(Color.parseColor(uiConfig.setPrivacyAlertOneColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertOneColor: " + uiConfig.setPrivacyAlertOneColor)
                }
            }
            if (uiConfig.setPrivacyAlertTwoColor != "") {
                authUIConfig.setPrivacyAlertTwoColor(Color.parseColor(uiConfig.setPrivacyAlertTwoColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertTwoColor: " + uiConfig.setPrivacyAlertTwoColor)
                }
            }
            if (uiConfig.setPrivacyAlertThreeColor != "") {
                authUIConfig.setPrivacyAlertThreeColor(Color.parseColor(uiConfig.setPrivacyAlertThreeColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertThreeColor: " + uiConfig.setPrivacyAlertThreeColor)
                }
            }
            if (uiConfig.setPrivacyAlertOperatorColor != "") {
                authUIConfig.setPrivacyAlertOperatorColor(Color.parseColor(uiConfig.setPrivacyAlertOperatorColor))
                if (config.log) {
                    channel.invokeMethod("log ", "setPrivacyAlertOperatorColor: " + uiConfig.setPrivacyAlertOperatorColor)
                }
            }
            phoneNumberAuthHelper.setAuthUIConfig(authUIConfig.create())
        } else if (call.method == "getCurrentCarrierName") {
            result.success(phoneNumberAuthHelper.currentCarrierName)
        } else if (call.method == "getVersion") {
            result.success(PhoneNumberAuthHelper.getVersion())
        } else if (call.method == "logined") {
            phoneNumberAuthHelper.setUIClickListener(null)
        } else if (call.method == "getLoginToken") {
            //一键登录获取Token
            val timeout: Int = call.argument<Int>("timeout") ?: 5000
            try {
                if (this.isLoginAuth) {
                    phoneNumberAuthHelper.quitLoginPage()
                    this.isLoginAuth = true
                }
                phoneNumberAuthHelper.getLoginToken(activity, timeout)
                result.success(null)
            } catch (e: Exception) {
                result.error(e.toString(), "", null)
            }
        } else if (call.method == "quitLoginPage") {
            //注销登录页面
            this.isLoginAuth = false
            phoneNumberAuthHelper.quitLoginPage()
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
