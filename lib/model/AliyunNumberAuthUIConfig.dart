import 'dart:ffi';

/// 号码认证UI配置类
class AliyunNumberAuthUIConfig {
  /// 设置设置状态栏颜色 #fffff | #000000 纯色无效
  String? setStatusBarColor;

  /// 设置状态栏字体颜色 true：表示字体颜色为黑色 false：表示字体颜色为白色
  bool? setLightColor;

  /// 设置设置导航栏颜色 #fffff | #000000 纯色无效
  String? setNavColor;

  /// 设置导航栏标题文字
  String? setNavText;

  /// 设置导航栏标题文字颜色  #fffff | #000000 纯色无效
  String? setNavTextColor;

  /// 设置导航栏标题文字大小
  int? setNavTextSize;

  /// 设置导航栏返回按钮隐藏
  bool? setNavReturnHidden;

  /// 设置默认导航栏是否隐藏
  bool? setNavHidden;

  /// 设置状态栏是否隐藏
  bool? setStatusBarHidden;

  /// 设置状态栏UI属性
  /// 1:非全屏显示状态，状态栏的部分图标会被隐藏
  /// 2:全屏显示
  int? setStatusBarUIFlag;

  /// 设置协议页顶部导航栏背景色 #fffff | #000000 纯色无效
  String? setWebViewStatusBarColor;

  /// 设置协议页顶部导航栏背景色 #fffff | #000000 纯色无效
  String? setWebNavColor;

  /// 设置协议页顶部导航栏标题颜色 #fffff | #000000 纯色无效
  String? setWebNavTextColor;

  /// 设置协议页顶部导航栏文字大小
  int? setWebNavTextSize;

  /// 设置底部虚拟按键背景色 #fffff | #000000 纯色无效
  String? setBottomNavColor;

  /// 设置导航栏标题文字大小
  int? setNavTextSizeDp;

  /// 设置Logo是否隐藏
  Bool? setLogoHidden;

  /// 设置Logo图片路径（即drawable下文件名称，但无需带文件格式）
  String? setLogoImgPath;

  /// 设置Logo控件宽度（单位：dp）
  int? setLogoWidth;

  /// 设置Logo控件高度（单位：dp）
  int? setLogoHeight;

  /// 设置Logo控件相对导航栏顶部的位移（单位：dp）
  int? setLogoOffsetY;

  /// 设置Logo控件相对底部的位移（单位：dp）
  int? setLogoOffsetY_B;

  /// 设置Logo图片路径（即drawable下文件名称，但无需带文件格式）
  /// 1: center：保持原图大小，显示在ImageView的中心。当原图的size大于ImageView的size，超过部分裁剪处理
  /// 2: centerCrop：原图的中心对准ImageView的中心，等比例放大原图，直到填满ImageView为止，对原图超过ImageView的部分做裁剪处理
  /// 3: centerInside：图片居中显示，按比例缩小原图的size等于或小于ImageView。如果原图的size本身就小于ImageView的size，则原图的size不做任何处理，居中显示在ImageView
  /// 4: matrix：不改变原图的大小，从ImageView左上角绘制原图，超过部分裁剪处理
  /// 5: fitCenter：原图按比例扩大或缩小到ImageView的ImageView的高度，居中显示
  /// 6: fitEnd：原图按比例扩大或缩小到ImageView的高度，显示在ImageView的下部分位置
  /// 7: fitStart：原图按比例扩大或缩小到ImageView的高度，显示在ImageView的上部分位置
  /// 8: 原图按照指定的大小在View中显示，拉伸显示图片，不保持原比例，填满ImageView
  int? setLogoScaleType;

  /// 设置Slogan文字内容
  String? setSloganText;

  /// 设置Slogan文字颜色 #fffff | #000000 纯色无效
  String? setSloganTextColor;

  /// 设置Slogan文字大小
  int? setSloganTextSize;

  /// 设置Slogan相对导航栏顶部的位移（单位：dp）
  int? setSloganOffsetY;

  /// 设置Slogan相对底部的位移（单位：dp）。
  int? setSloganOffsetY_B;

  /// 设置Slogan文字大小（单位：dp，字体大小不随系统变化）
  int? setSloganTextSizeDp;

  /// 设置手机号码字体颜色#fffff | #000000 纯色无效
  String? setNumberColor;

  /// 设置手机号码字体大小
  int? setNumberSize;

  /// 设置号码栏控件相对导航栏顶部的位移（单位：dp）
  int? setNumFieldOffsetY;

  /// 设置号码栏控件相对底部的位移（单位：dp）
  int? setNumFieldOffsetY_B;

  ///（单位：dp）
  int? setNumberFieldOffsetX;

  /// 设置手机号掩码的布局对齐方式，支持以下三种对齐方式
  /// 1: Gravity.CENTER_HORIZONTAL：水平居中
  /// 2: Gravity.LEFT：左对齐
  /// 3: Gravity.RIGHT：右对齐
  int? setNumberLayoutGravity;

  /// 设置手机号码字体大小（单位：dp，字体大小不随系统变化）
  int? setNumberSizeDp;

  /// 设置手机号码文本是否使用自定义字体。自定义字体名称，包含字体格式如xxx.ttf，文件需放置在assets下
  List<String>? useNumberFontAndPath;

  /// 设置手机号码文本使用系统字体 Typeface
  /// 1: Typeface.SANS_SERIF
  /// 2: Typeface.SERIF
  /// 3: Typeface.MONOSPACE
  int? setNumberTypeface;

  /// 设置手机号码显示水平间距
  double? setNumberTextSpace;

  /// 设置登录按钮文字
  String? setLogBtnText;

  /// 设置登录按钮文字颜色
  String? setLogBtnTextColor;

  /// 设置登录按钮文字大小
  int? setLogBtnTextSize;

  /// 设置登录按钮宽度（单位：dp）
  int? setLogBtnWidth;

  /// 设置登录按钮高度（单位：dp）
  int? setLogBtnHeight;

  /// 设置登录按钮相对于屏幕左右边缘边距（单位：dp）
  int? setLogBtnMarginLeftAndRight;

  /// 设置登录按钮背景图片的路径
  String? setLogBtnBackgroundPath;

  /// 设置登录按钮相对导航栏顶部的位移（单位：dp）
  int? setLogBtnOffsetY;

  /// 设置登录按钮相对底部的位移（单位：dp）
  int? setLogBtnOffsetY_B;

  /// 设置登录loading dialog背景图片的路径
  String? setLoadingImgPath;

  /// 设置登录按钮X轴偏移量
  /// 如果设置了setLogBtnMarginLeftAndRight，并且布局对齐方式为左对齐或右对齐;则会在margin的基础上再增加offsetX的偏移量，如果是居中对齐，则会在居中的基础上再做offsetX的偏移
  int? setLogBtnOffsetX;

  /// 设置登录按钮布局对齐方式，支持以下三种对齐方式
  /// 1: Gravity.CENTER_HORIZONTAL：水平居中
  /// 2: Gravity.LEFT：左对齐
  /// 3: Gravity.RIGHT：右对齐
  int? setLogBtnLayoutGravity;

  /// 设置登录按钮文字大小（单位：dp，字体大小不随系统变化）
  int? setLogBtnTextSizeDp;

  /// 设置登录按钮背景图片的drawable对象
  String? setLogBtnBackgroundDrawable;

  /// 设置登录文本是否使用自定义字体。自定义字体名称，包含字体格式，如xxx.ttf，文件需放置在assets下
  String? setLoadingImgDrawable;

  /// 设置登录按钮文字
  List<String>? useLogBtnFontAndPath;

  /// 设置登录按钮文字 Typeface
  /// 1: Typeface.SANS_SERIF
  /// 2: Typeface.SERIF
  /// 3: Typeface.MONOSPACE
  int? setLogBtnTypeface;

  /// 自定义协议1
  List<String>? setAppPrivacyOne;

  /// 自定义协议2
  List<String>? setAppPrivacyTwo;

  /// 自定义协议2
  List<String>? setAppPrivacyThree;

  /// 设置隐私条款名称颜色（基础文字颜色，协议文字颜色）
  List<String>? setAppPrivacyColor;

  /// 设置隐私条款相对导航栏顶部的位移（单位：dp）
  int? setPrivacyOffsetY;

  /// 设置隐私条款相对底部的位移（单位：dp）
  int? setPrivacyOffsetY_B;

  /// 设置隐私条款是否默认勾选
  bool? setPrivacyState;

  /// 设置隐私条款文字大小（单位：sp）
  int? setPrivacyTextSize;

  /// 设置运营商协议前缀符号，只能设置一个字符，且只能设置<>、()、《》、【】、『』、[]、（）中的一个
  String? setVendorPrivacyPrefix;

  /// 设置运营商协议后缀符号，只能设置一个字符，且只能设置<>、()、《》、【】、『』、[]、（）中的一个
  String? setVendorPrivacySuffix;

  /// 设置授权页协议1文本颜色
  String? setPrivacyOneColor;

  /// 设置授权页协议2文本颜色
  String? setPrivacyTwoColor;

  /// 设置授权页协议3文本颜色
  String? setPrivacyThreeColor;

  /// 设置授权页运营商协议文本颜色
  String? setPrivacyOperatorColor;

  /// 设置隐私协议栏复选框宽度（单位：dp）
  int? setCheckBoxWidth;

  /// 设置隐私协议栏复选框高度（单位：dp）
  int? setCheckBoxHeight;

  /// 设置切换按钮点是否可见
  bool? setSwitchAccHidden;

  /// 设置切换按钮文字内容
  String? setSwitchAccText;

  /// 设置切换按钮文字颜色
  String? setSwitchAccTextColor;

  /// 设置切换按钮文字大小
  int? setSwitchAccTextSize;

  /// 设置切换按钮相对导航栏顶部的位移（单位：dp）
  int? setSwitchOffsetY;

  /// 设置切换按钮相对底部的位移（单位：dp）
  int? setSwitchOffsetY_B;

  /// 设置切换按钮文字大小（单位：dp，字体大小不随系统变化）
  int? setSwitchAccTextSizeDp;

  /// 设置切换按钮点是否可见
  List<String>? useSwitchFontAndPath;

  /// 设置切换按钮点是否可见 Typeface
  /// 1: Typeface.SANS_SERIF
  /// 2: Typeface.SERIF
  /// 3: Typeface.MONOSPACE
  bool? setSwitchTypeface;

  /// 设置授权页进场动画（即anim下文件名称，但无需带文件格式）
  /// 图片或者xml的传参方式为不包含后缀名的全称 需要文件需要放在drawable或drawable-xxx目录下 in_activity.xml, mytel_app_launcher.png
  List<String>? setAuthPageActIn;

  /// 设置授权页退出动画（即anim下文件名称，但无需带文件格式） 例: ["in_activity", "out_activity"]
  /// 图片或者xml的传参方式为不包含后缀名的全称 需要文件需要放在drawable或drawable-xxx目录下 in_activity.xml, mytel_app_launcher.png
  List<String>? setAuthPageActOut;

  /// 设置授权页背景图
  /// drawable资源的目录，不需要加后缀，如图片在drawable中的存放目录是res/drawable-xxhdpi/loading.png,则传入参数为"loading"，setPageBackgroundPath("loading")
  String? setPageBackgroundPath;

  /// 设置弹窗模式授权页宽度（单位：dp）,设置大于0，即为弹窗模式
  int? setDialogWidth;

  /// 设置弹窗模式授权页高度（单位：dp），设置大于0，即为弹窗模式
  int? setDialogHeight;

  /// 设置弹窗模式授权页高度（单位：dp），设置大于0，即为弹窗模式
  /// 值为0时，表示完全透明、值为1时，表示完全不透明、值为1时，表示完全不透明
  double? setDialogAlpha;

  /// 设置弹窗模式授权页X轴偏移（单位：dp）
  int? setDialogoffsetX;

  /// 设置弹窗模式授权页Y轴偏移（单位：dp）
  int? setDialogoffsetY;

  /// 设置授权页弹窗模式，点击非弹窗区域关闭授权页
  bool? setTapAuthPageMaskClosePage;

  /// 设置授权页是否居于底部
  bool? setDialogBottom;

  /// 自定义协议页跳转Action
  String? setProtocolAction;

  /// 配置自定义协议页跳转Action必须配置这个属性，值为App的包名
  String? setPackageName;

  /// 自定义协议页跳转Action
  int? setWebCacheMode;

  /// 设置二次隐私协议弹窗是否需要显示
  bool? setPrivacyAlertIsNeedShow;

  /// 设置二次隐私协议弹窗点击按钮是否需要执行登录
  bool? setPrivacyAlertIsNeedAutoLogin;

  /// 设置二次隐私协议弹窗背景蒙层是否显示
  bool? setPrivacyAlertMaskIsNeedShow;

  /// 设置二次隐私协议弹窗蒙层透明度。默认值0.3
  double? setPrivacyAlertMaskAlpha;

  /// 设置二次隐私协议弹窗透明度。默认值1.0
  double? setPrivacyAlertAlpha;

  /// 设置二次隐私协议弹窗背景色（同意并继续按钮区域）
  String? setPrivacyAlertBackgroundColor;

  /// 设置二次隐私协议弹窗显示自定义动画
  String? setPrivacyAlertEntryAnimation;

  /// 设置二次隐私协议弹窗隐藏自定义动画
  String? setPrivacyAlertExitAnimation;

  /// 设置二次隐私协议弹窗的四个圆角值 顺序为左上、右上、右下、左下，需要填充4个值，不足4个值则无效，如果值小于等于0则为直角
  List<int>? setPrivacyAlertCornerRadiusArray;

  /// 设置屏幕居中、居上、居下、居左、居右，默认居中显示
  int? setPrivacyAlertAlignment;

  /// 设置弹窗宽度
  int? setPrivacyAlertWidth;

  /// 设置弹窗高度
  int? setPrivacyAlertHeight;

  /// 设置弹窗水平偏移量。（单位：dp）
  int? setPrivacyAlertOffsetX;

  /// 设置弹窗竖直偏移量。（单位：dp）
  int? setPrivacyAlertOffsetY;

  /// 设置二次隐私协议弹窗标题背景颜色
  String? setPrivacyAlertTitleBackgroundColor;

  /// 设置二次隐私协议弹窗标题支持居中、居左，默认居中显示
  int? setPrivacyAlertTitleAlignment;

  /// 设置标题文字水平偏移量。（单位：dp）
  int? setPrivacyAlertTitleOffsetX;

  /// 设置标题文字竖直偏移量。（单位：dp）
  int? setPrivacyAlertTitleOffsetY;

  /// 设置标题文本
  String? setPrivacyAlertTitleContent;

  /// 设置标题文字大小，默认值18 sp
  int? setPrivacyAlertTitleTextSize;

  /// 设置标题文字颜色
  String? setPrivacyAlertTitleColor;

  /// 设置协议内容背景颜色
  String? setPrivacyAlertContentBackgroundColor;

  /// 设置服务协议文字大小，默认值16 sp
  int? setPrivacyAlertContentTextSize;

  /// 设置二次隐私协议弹窗协议文案支持居中、居左，默认居左显示
  int? setPrivacyAlertContentAlignment;

  /// 设置服务协议文字颜色
  String? setPrivacyAlertContentColor;

  /// 设置服务协议非协议文字颜色
  String? setPrivacyAlertContentBaseColor;

  /// 设置服务协议左右两侧间距
  int? setPrivacyAlertContentHorizontalMargin;

  /// 设置服务协议上下间距
  int? setPrivacyAlertContentVerticalMargin;

  /// 设置按钮背景图片路径
  String? setPrivacyAlertBtnBackgroundImgPath;

  /// 设置确认按钮文字颜色
  String? setPrivacyAlertBtnTextColor;

  /// 设置确认按钮文字大小，默认值18 sp
  int? setPrivacyAlertBtnTextSize;

  /// 设置确认按钮宽度。（单位：dp）
  int? setPrivacyAlertBtnWidth;

  /// 设置确认按钮高度。（单位：dp）
  int? setPrivacyAlertBtnHeigth;

  /// 设置右上角的关闭按钮
  bool? setPrivacyAlertCloseBtnShow;

  /// 设置关闭按钮图片路径
  String? setPrivacyAlertCloseImagPath;

  /// 设置关闭按钮宽度。（单位：dp）
  int? setPrivacyAlertCloseImgWidth;

  /// 设置关闭按钮高度。（单位：dp）
  int? setPrivacyAlertCloseImgHeight;

  /// 设置确认按钮文本
  String? setPrivacyAlertBtnContent;

  /// 设置二次弹窗确定按钮横向坐标
  int? setPrivacyAlertBtnOffsetX;

  /// 设置二次弹窗确定按钮纵向坐标
  int? setPrivacyAlertBtnOffsetY;

  /// 设置确认按钮的横向边距
  int? setPrivacyAlertBtnHorizontalMargin;

  /// 设置确认按钮的纵向边距
  int? setPrivacyAlertBtnVerticalMargin;

  /// 设置二次隐私协议弹窗点击背景蒙层是否关闭弹窗
  bool? setTapPrivacyAlertMaskCloseAlert;

  /// 二次弹窗协议前缀
  String? setPrivacyAlertBefore;

  /// 二次弹窗协议后缀
  String? setPrivacyAlertEnd;

  /// 设置授权页协议1文本颜色
  String? setPrivacyAlertOneColor;

  /// 设置授权页协议2文本颜色
  String? setPrivacyAlertTwoColor;

  /// 设置授权页协议3文本颜色
  String? setPrivacyAlertThreeColor;

  /// 设置授权页运营商协议文本颜色
  String? setPrivacyAlertOperatorColor;

  /// 实例化配置
  ///
  /// [setAppPrivacyOne] 隐私协议1 [文本,url]
  AliyunNumberAuthUIConfig({
    /// 导航栏
    this.setStatusBarColor,
    this.setLightColor,
    this.setNavColor,
    this.setNavText,
    this.setNavTextColor,
    this.setNavTextSize,
    this.setNavReturnHidden,
    this.setNavHidden,
    this.setStatusBarHidden,
    this.setStatusBarUIFlag,
    this.setWebViewStatusBarColor,
    this.setWebNavColor,
    this.setWebNavTextColor,
    this.setWebNavTextSize,
    this.setBottomNavColor,
    this.setNavTextSizeDp,

    //logo
    this.setLogoHidden,
    this.setLogoImgPath,
    this.setLogoWidth,
    this.setLogoHeight,
    this.setLogoOffsetY,
    this.setLogoOffsetY_B,
    this.setLogoScaleType,

    // slogan
    this.setSloganText,
    this.setSloganTextColor,
    this.setSloganTextSize,
    this.setSloganOffsetY,
    this.setSloganOffsetY_B,
    this.setSloganTextSizeDp,

    /// 授权页号码栏
    this.setNumberColor,
    this.setNumberSize,
    this.setNumFieldOffsetY,
    this.setNumFieldOffsetY_B,
    this.setNumberFieldOffsetX,
    this.setNumberLayoutGravity,
    this.setNumberSizeDp,
    this.useNumberFontAndPath,
    this.setNumberTypeface,
    this.setNumberTextSpace,

    /// 授权页按钮
    this.setLogBtnText,
    this.setLogBtnTextColor,
    this.setLogBtnTextSize,
    this.setLogBtnWidth,
    this.setLogBtnHeight,
    this.setLogBtnMarginLeftAndRight,
    this.setLogBtnBackgroundPath,
    this.setLogBtnOffsetY,
    this.setLogBtnOffsetY_B,
    this.setLoadingImgPath,
    this.setLogBtnOffsetX,
    this.setLogBtnLayoutGravity,
    this.setLogBtnTextSizeDp,
    this.setLogBtnBackgroundDrawable,
    this.setLoadingImgDrawable,
    this.useLogBtnFontAndPath,
    this.setLogBtnTypeface,

    /// 授权页隐私栏
    this.setAppPrivacyOne,
    this.setAppPrivacyTwo,
    this.setAppPrivacyThree,
    this.setAppPrivacyColor,
    this.setPrivacyOffsetY,
    this.setPrivacyOffsetY_B,
    this.setPrivacyState,
    this.setPrivacyTextSize,
    this.setVendorPrivacyPrefix,
    this.setVendorPrivacySuffix,
    this.setPrivacyOneColor,
    this.setPrivacyTwoColor,
    this.setPrivacyThreeColor,
    this.setPrivacyOperatorColor,
    this.setCheckBoxWidth,
    this.setCheckBoxHeight,

    /// 切换控件（切换登录按钮）方式
    this.setSwitchAccHidden,
    this.setSwitchAccText,
    this.setSwitchAccTextColor,
    this.setSwitchAccTextSize,
    this.setSwitchOffsetY,
    this.setSwitchOffsetY_B,
    this.setSwitchAccTextSizeDp,
    this.useSwitchFontAndPath,
    this.setSwitchTypeface,

    /// 授权页
    this.setAuthPageActIn,
    this.setAuthPageActOut,
    this.setPageBackgroundPath,
    this.setDialogWidth,
    this.setDialogHeight,
    this.setDialogAlpha,
    this.setDialogoffsetX,
    this.setDialogoffsetY,
    this.setTapAuthPageMaskClosePage,
    this.setDialogBottom,
    this.setProtocolAction,
    this.setPackageName,
    this.setWebCacheMode,

    //二次弹窗
    this.setPrivacyAlertIsNeedShow,
    this.setPrivacyAlertIsNeedAutoLogin,
    this.setPrivacyAlertMaskIsNeedShow,
    this.setPrivacyAlertMaskAlpha,
    this.setPrivacyAlertAlpha,
    this.setPrivacyAlertBackgroundColor,
    this.setPrivacyAlertEntryAnimation,
    this.setPrivacyAlertExitAnimation,
    this.setPrivacyAlertCornerRadiusArray,
    this.setPrivacyAlertAlignment,
    this.setPrivacyAlertWidth,
    this.setPrivacyAlertHeight,
    this.setPrivacyAlertOffsetX,
    this.setPrivacyAlertOffsetY,
    this.setPrivacyAlertTitleBackgroundColor,
    this.setPrivacyAlertTitleAlignment,
    this.setPrivacyAlertTitleOffsetX,
    this.setPrivacyAlertTitleOffsetY,
    this.setPrivacyAlertTitleContent,
    this.setPrivacyAlertTitleTextSize,
    this.setPrivacyAlertTitleColor,
    this.setPrivacyAlertContentBackgroundColor,
    this.setPrivacyAlertContentTextSize,
    this.setPrivacyAlertContentAlignment,
    this.setPrivacyAlertContentColor,
    this.setPrivacyAlertContentBaseColor,
    this.setPrivacyAlertContentHorizontalMargin,
    this.setPrivacyAlertContentVerticalMargin,
    this.setPrivacyAlertBtnBackgroundImgPath,
    this.setPrivacyAlertBtnTextColor,
    this.setPrivacyAlertBtnTextSize,
    this.setPrivacyAlertBtnWidth,
    this.setPrivacyAlertBtnHeigth,
    this.setPrivacyAlertCloseBtnShow,
    this.setPrivacyAlertCloseImagPath,
    this.setPrivacyAlertCloseImgWidth,
    this.setPrivacyAlertCloseImgHeight,
    this.setPrivacyAlertBtnContent,
    this.setPrivacyAlertBtnOffsetX,
    this.setPrivacyAlertBtnOffsetY,
    this.setPrivacyAlertBtnHorizontalMargin,
    this.setPrivacyAlertBtnVerticalMargin,
    this.setTapPrivacyAlertMaskCloseAlert,
    this.setPrivacyAlertBefore,
    this.setPrivacyAlertEnd,
    this.setPrivacyAlertOneColor,
    this.setPrivacyAlertTwoColor,
    this.setPrivacyAlertThreeColor,
    this.setPrivacyAlertOperatorColor,
  });

  // 将对象转换为 JSON 格式的 Map
  Map<String, dynamic> toJson() {
    return {
      'setStatusBarColor': setStatusBarColor,
      'setLightColor': setLightColor,
      'setNavColor': setNavColor,
      'setNavText': setNavText,
      'setNavTextColor': setNavTextColor,
      'setNavTextSize': setNavTextSize,
      'setNavReturnHidden': setNavReturnHidden,
      'setNavHidden': setNavHidden,
      'setStatusBarHidden': setStatusBarHidden,
      'setStatusBarUIFlag': setStatusBarUIFlag,
      'setWebViewStatusBarColor': setWebViewStatusBarColor,
      'setWebNavColor': setWebNavColor,
      'setWebNavTextColor': setWebNavTextColor,
      'setWebNavTextSize': setWebNavTextSize,
      'setBottomNavColor': setBottomNavColor,
      'setNavTextSizeDp': setNavTextSizeDp,
      'setLogoHidden': setLogoHidden,
      'setLogoImgPath': setLogoImgPath,
      'setLogoWidth': setLogoWidth,
      'setLogoHeight': setLogoHeight,
      'setLogoOffsetY': setLogoOffsetY,
      'setLogoOffsetY_B': setLogoOffsetY_B,
      'setLogoScaleType': setLogoScaleType,
      'setSloganText': setSloganText,
      'setSloganTextColor': setSloganTextColor,
      'setSloganTextSize': setSloganTextSize,
      'setSloganOffsetY': setSloganOffsetY,
      'setSloganOffsetY_B': setSloganOffsetY_B,
      'setSloganTextSizeDp': setSloganTextSizeDp,
      'setNumberColor': setNumberColor,
      'setNumberSize': setNumberSize,
      'setNumFieldOffsetY': setNumFieldOffsetY,
      'setNumFieldOffsetY_B': setNumFieldOffsetY_B,
      'setNumberFieldOffsetX': setNumberFieldOffsetX,
      'setNumberLayoutGravity': setNumberLayoutGravity,
      'setNumberSizeDp': setNumberSizeDp,
      'useNumberFontAndPath': useNumberFontAndPath,
      'setNumberTypeface': setNumberTypeface,
      'setNumberTextSpace': setNumberTextSpace,
      'setLogBtnText': setLogBtnText,
      'setLogBtnTextColor': setLogBtnTextColor,
      'setLogBtnTextSize': setLogBtnTextSize,
      'setLogBtnWidth': setLogBtnWidth,
      'setLogBtnHeight': setLogBtnHeight,
      'setLogBtnMarginLeftAndRight': setLogBtnMarginLeftAndRight,
      'setLogBtnBackgroundPath': setLogBtnBackgroundPath,
      'setLogBtnOffsetY': setLogBtnOffsetY,
      'setLogBtnOffsetY_B': setLogBtnOffsetY_B,
      'setLoadingImgPath': setLoadingImgPath,
      'setLogBtnOffsetX': setLogBtnOffsetX,
      'setLogBtnLayoutGravity': setLogBtnLayoutGravity,
      'setLogBtnTextSizeDp': setLogBtnTextSizeDp,
      'setLogBtnBackgroundDrawable': setLogBtnBackgroundDrawable,
      'setLoadingImgDrawable': setLoadingImgDrawable,
      'useLogBtnFontAndPath': useLogBtnFontAndPath,
      'setLogBtnTypeface': setLogBtnTypeface,
      'setAppPrivacyOne': setAppPrivacyOne,
      'setAppPrivacyTwo': setAppPrivacyTwo,
      'setAppPrivacyThree': setAppPrivacyThree,
      'setAppPrivacyColor': setAppPrivacyColor,
      'setPrivacyOffsetY': setPrivacyOffsetY,
      'setPrivacyOffsetY_B': setPrivacyOffsetY_B,
      'setPrivacyState': setPrivacyState,
      'setPrivacyTextSize': setPrivacyTextSize,
      'setVendorPrivacyPrefix': setVendorPrivacyPrefix,
      'setVendorPrivacySuffix': setVendorPrivacySuffix,
      'setPrivacyOneColor': setPrivacyOneColor,
      'setPrivacyTwoColor': setPrivacyTwoColor,
      'setPrivacyThreeColor': setPrivacyThreeColor,
      'setPrivacyOperatorColor': setPrivacyOperatorColor,
      'setCheckBoxWidth': setCheckBoxWidth,
      'setCheckBoxHeight': setCheckBoxHeight,
      "setSwitchAccHidden": setSwitchAccHidden,
      "setSwitchAccText": setSwitchAccText,
      'setSwitchAccTextColor': setSwitchAccTextColor,
      'setSwitchAccTextSize': setSwitchAccTextSize,
      'setSwitchOffsetY': setSwitchOffsetY,
      'setSwitchOffsetY_B': setSwitchOffsetY_B,
      'setSwitchAccTextSizeDp': setSwitchAccTextSizeDp,
      'useSwitchFontAndPath': useSwitchFontAndPath,
      'setSwitchTypeface': setSwitchTypeface,
      'setAuthPageActIn': setAuthPageActIn,
      'setAuthPageActOut': setAuthPageActOut,
      'setPageBackgroundPath': setPageBackgroundPath,
      'setDialogWidth': setDialogWidth,
      'setDialogHeight': setDialogHeight,
      'setDialogAlpha': setDialogAlpha,
      'setDialogoffsetX': setDialogoffsetX,
      'setDialogoffsetY': setDialogoffsetY,
      'setTapAuthPageMaskClosePage': setTapAuthPageMaskClosePage,
      'setDialogBottom': setDialogBottom,
      'setProtocolAction': setProtocolAction,
      'setPackageName': setPackageName,
      'setWebCacheMode': setWebCacheMode,
      'setPrivacyAlertIsNeedShow': setPrivacyAlertIsNeedShow,
      'setPrivacyAlertIsNeedAutoLogin': setPrivacyAlertIsNeedAutoLogin,
      'setPrivacyAlertMaskIsNeedShow': setPrivacyAlertMaskIsNeedShow,
      'setPrivacyAlertMaskAlpha': setPrivacyAlertMaskAlpha,
      'setPrivacyAlertAlpha': setPrivacyAlertAlpha,
      'setPrivacyAlertBackgroundColor': setPrivacyAlertBackgroundColor,
      'setPrivacyAlertEntryAnimation': setPrivacyAlertEntryAnimation,
      'setPrivacyAlertExitAnimation': setPrivacyAlertExitAnimation,
      'setPrivacyAlertCornerRadiusArray': setPrivacyAlertCornerRadiusArray,
      'setPrivacyAlertAlignment': setPrivacyAlertAlignment,
      'setPrivacyAlertWidth': setPrivacyAlertWidth,
      'setPrivacyAlertHeight': setPrivacyAlertHeight,
      'setPrivacyAlertOffsetX': setPrivacyAlertOffsetX,
      'setPrivacyAlertOffsetY': setPrivacyAlertOffsetY,
      'setPrivacyAlertTitleBackgroundColor':
          setPrivacyAlertTitleBackgroundColor,
      'setPrivacyAlertTitleAlignment': setPrivacyAlertTitleAlignment,
      'setPrivacyAlertTitleOffsetX': setPrivacyAlertTitleOffsetX,
      'setPrivacyAlertTitleOffsetY': setPrivacyAlertTitleOffsetY,
      'setPrivacyAlertTitleContent': setPrivacyAlertTitleContent,
      'setPrivacyAlertTitleTextSize': setPrivacyAlertTitleTextSize,
      'setPrivacyAlertTitleColor': setPrivacyAlertTitleColor,
      'setPrivacyAlertContentBackgroundColor':
          setPrivacyAlertContentBackgroundColor,
      'setPrivacyAlertContentTextSize': setPrivacyAlertContentTextSize,
      'setPrivacyAlertContentAlignment': setPrivacyAlertContentAlignment,
      'setPrivacyAlertContentColor': setPrivacyAlertContentColor,
      'setPrivacyAlertContentBaseColor': setPrivacyAlertContentBaseColor,
      'setPrivacyAlertContentHorizontalMargin':
          setPrivacyAlertContentHorizontalMargin,
      'setPrivacyAlertContentVerticalMargin':
          setPrivacyAlertContentVerticalMargin,
      'setPrivacyAlertBtnBackgroundImgPath':
          setPrivacyAlertBtnBackgroundImgPath,
      'setPrivacyAlertBtnTextColor': setPrivacyAlertBtnTextColor,
      'setPrivacyAlertBtnTextSize': setPrivacyAlertBtnTextSize,
      'setPrivacyAlertBtnWidth': setPrivacyAlertBtnWidth,
      'setPrivacyAlertBtnHeigth': setPrivacyAlertBtnHeigth,
      'setPrivacyAlertCloseBtnShow': setPrivacyAlertCloseBtnShow,
      'setPrivacyAlertCloseImagPath': setPrivacyAlertCloseImagPath,
      'setPrivacyAlertCloseImgWidth': setPrivacyAlertCloseImgWidth,
      'setPrivacyAlertCloseImgHeight': setPrivacyAlertCloseImgHeight,
      'setPrivacyAlertBtnContent': setPrivacyAlertBtnContent,
      'setPrivacyAlertBtnOffsetX': setPrivacyAlertBtnOffsetX,
      'setPrivacyAlertBtnOffsetY': setPrivacyAlertBtnOffsetY,
      'setPrivacyAlertBtnHorizontalMargin': setPrivacyAlertBtnHorizontalMargin,
      'setPrivacyAlertBtnVerticalMargin': setPrivacyAlertBtnVerticalMargin,
      'setTapPrivacyAlertMaskCloseAlert': setTapPrivacyAlertMaskCloseAlert,
      'setPrivacyAlertBefore': setPrivacyAlertBefore,
      'setPrivacyAlertEnd': setPrivacyAlertEnd,
      'setPrivacyAlertOneColor': setPrivacyAlertOneColor,
      'setPrivacyAlertTwoColor': setPrivacyAlertTwoColor,
      'setPrivacyAlertThreeColor': setPrivacyAlertThreeColor,
      'setPrivacyAlertOperatorColor': setPrivacyAlertOperatorColor,
    };
  }
}
