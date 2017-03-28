#Android 混淆打包


[ProGuard 官网](https://www.guardsquare.com/en/proguard)

[Android 官方文档](https://developer.android.google.cn/studio/build/shrink-code.html)

----
**准备工作：**
在混淆之前可以看看[Android 反编译技术](http://unclechen.github.io/2016/09/07/Android%E5%8F%8D%E7%BC%96%E8%AF%91%E6%8A%80%E6%9C%AF%E6%80%BB%E7%BB%93/)这篇博文怎么把一个apk 完整的反编译，熟悉一下就可以了，我们这里没必要搞那么复杂，google 早就为我们准备好了方便查看apk 结构的工具 用于查看混淆之后的结果足够了 分享github地址[google/android-classyshark](https://github.com/google/android-classyshark) 下载jar文件 直接运行 打开apk即可，如图


##图片

**首先介绍一下Android 代码混淆的工具 ProGuard**

##ProGuard Android 中代码混淆工具


* 压缩(Shrink):侦测并移除代码中无用的类、字段、方法、和特性(Attribute)。
* 优化(OPtimize):对字节码进行优化，移除无用指令。
* 混淆(Obfuscate):使用a、b、c、d这样简短而无意义的名称，对类、字段和方法进行重命名。
* 预检(Preveirfy): 在java平台上对处理后的代码进行预检 (android 中不需要预检)


###在ant／eclipse 项目中ProGuard的启用方法
在项目的project.properties 中添加如下代码

```
proguard.config=proguard.cfg //proguard.cfg为proguard的配置文件
proguard.config=/path/to/proguard.cfg //路径不在项目根目录时，填写实际路径

```
###在Gradle项目中（Android Studio）

图片

在build.gradle 中的android下添加proguard的配置文件

```
 buildTypes {
        release {
            // 混淆
            minifyEnabled true
            // Zipalign优化
            zipAlignEnabled true
            // 移除无用的resource文件
            shrinkResources true
            // 前一部分代表系统默认的android程序的混淆文件，该文件已经包含了基本的混淆声明，后一个文件是自己的定义混淆文件
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'

        }
```
然后在 proguard-rules.pro 文件中进行配置

我这个工程有两个module 实测中只要在主工程中配置就可以了

###ProGuard 配置文件的一些语法
* 保留选项（配置不进行处理的内容）

```
-keep {Modifier} {class_specification} 保护指定的类文件和类的成员
-keepclassmembers {modifier} {class_specification} 保护指定类的成员，如果此类受到保护他们会保护的更好
-keepclasseswithmembers {class_specification} 保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在。
-keepnames {class_specification} 保护指定的类和类的成员的名称（如果他们不会压缩步骤中删除）
-keepclassmembernames {class_specification} 保护指定的类的成员的名称（如果他们不会压缩步骤中删除）
-keepclasseswithmembernames {class_specification} 保护指定的类和类的成员的名称，如果所有指定的类成员出席（在压缩步骤之后）
-printseeds {filename} 列出类和类的成员-keep选项的清单，标准输出到给定的文件
```

* 压缩

```
-dontshrink 不压缩输入的类文件
-printusage {filename}
-whyareyoukeeping {class_specification}
优化
-dontoptimize 不优化输入的类文件
-assumenosideeffects {class_specification} 优化时假设指定的方法，没有任何副作用
-allowaccessmodification 优化时允许访问并修改有修饰符的类和类的成员
```

* 混淆

```
-dontobfuscate 不混淆输入的类文件
-obfuscationdictionary {filename} 使用给定文件中的关键字作为要混淆方法的名称
-overloadaggressively 混淆时应用侵入式重载
-useuniqueclassmembernames 确定统一的混淆类的成员名称来增加混淆
-flattenpackagehierarchy {package_name} 重新包装所有重命名的包并放在给定的单一包中
-repackageclass {package_name} 重新包装所有重命名的类文件中放在给定的单一包中
-dontusemixedcaseclassnames 混淆时不会产生形形色色的类名
-keepattributes {attribute_name,…} 保护给定的可选属性，例如LineNumberTable, LocalVariableTable, SourceFile, Deprecated, Synthetic, Signature, and InnerClasses.
-renamesourcefileattribute {string} 设置源文件中给定的字符串常量
```

* 后面的文件名，类名，或者包名等可以使用占位符代替

```
？表示一个字符
*可以匹配多个字符，但是如果是一个类，不会匹配其前面的包名
** 可以匹配多个字符，会匹配前面的包名。
```
在android中在android Manifest文件中的activity，service，provider， receviter，等都不能进行混淆。一些在xml中配置的view也不能进行混淆，android提供的默认配置中都有。


##proguard-rules.pro 文件配置
###基本配置

```
## 对于一些基本指令的添加####代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 这句话能够使我们的项目混淆后产生映射文件# 包含有类名->混淆后类名的映射关系
-verbose
#优化 不优化输入的类文件
-dontoptimize
#忽略警告
-ignorewarnings
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

############################################### Android开发中一些需要保留的公共部分##### 保留我们使用的四大组件，自定义的Application等等这些类不被混淆# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {    native <methods>;}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{    public void *(android.view.View);}

# 保留枚举类不被混淆
-keepclassmembers enum * {    public static **[] values();    public static ** valueOf(java.lang.String);}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{    *** get*();    void set*(***);    public <init>(android.content.Context);    public <init>(android.content.Context, android.util.AttributeSet);    public <init>(android.content.Context, android.util.AttributeSet, int);}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {    public static final android.os.Parcelable$Creator *;}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {    static final long serialVersionUID;    private static final java.io.ObjectStreamField[] serialPersistentFields;    !static !transient <fields>;    !private <fields>;    !private <methods>;    private void writeObject(java.io.ObjectOutputStream);    private void readObject(java.io.ObjectInputStream);    java.lang.Object writeReplace();    java.lang.Object readResolve();}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {    void *(**On*Event);    void *(**On*Listener);}

-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment


#如果引用了v4或者v7包，可以忽略警告，因为用不到android.support
-dontwarn android.support.**

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
```

####常用配置

```

################混淆保护自己项目的部分代码以及引用的第三方jar包library#########################
#-libraryjars libs/alipaysdk.jar
#-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/weibosdkcore.jar

-keep class com.scottfu.brightbook.bean.** {*;}


#友盟
-keep class com.umeng.**{*;}

#支付宝
-keep class com.alipay.android.app.IAliPay{*;}
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.lib.ResourceMap{*;}


#信鸽推送
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.mid.**  {* ;}


#极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

# universal-image-loader
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }


# volley
-dontwarn com.android.volley.**
-keep class com.android.volley.** { *; }

#glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *; }
```
注意 用于解析的数据模型 bean 不能混淆，要不然无法解析数据。
-keep class ......bean.** {*;}


ProGuard 输出文件

在gradle 方式下 在 /build/proguard目录下，在ant 方式下 在/bin/proguard 目录 在eclipse中在/proguard 目录下

* dump.txt 描述apk文件中所有类文件间的内部结构。
* mapping.txt 列出了原始的类，方法，和字段名与混淆后代码之间的映射。
* seeds.txt 列出了未被混淆的类和成员
* usage.txt 列出了从apk中删除的代码

当release版本出现bug的时候，通过以上文件找到错误的原始位置，进行bug修改。（特别是mapping.txt） 

注意在重新release编译后，这些文件就会被覆盖掉。


配置搞好后就进行打包。然后用 classyshark 查看结果，改一点打包一下，查看一下具体有什么变化。







###参考-第三方混淆配置

```
# AndroidEventBus
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}


# 百度地图（jar包换成自己的版本，记得签名要匹配）
-libraryjars libs/baidumapapi_v2_1_3.jar
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.sinovoice.** {*;}
-keep class pvi.com.** {*;}
-dontwarn com.baidu.**
-dontwarn vi.com.**
-dontwarn pvi.com.**


# Bugly
-dontwarn com.tencent.bugly.**
-keep class com.tencent.bugly.** {*;}


# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; } 
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }


# Facebook
-keep class com.facebook.** {*;}
-keep interface com.facebook.** {*;}
-keep enum com.facebook.** {*;}


# FastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*


# Fresco
-keep class com.facebook.fresco.** {*;}
-keep interface com.facebook.fresco.** {*;}
-keep enum com.facebook.fresco.** {*;}


# 高德相关依赖
# 集合包:3D地图3.3.2 导航1.8.0 定位2.5.0
-dontwarn com.amap.api.**
-dontwarn com.autonavi.**
-keep class com.amap.api.**{*;}
-keep class com.autonavi.**{*;}
# 地图服务
-dontwarn com.amap.api.services.**
-keep class com.map.api.services.** {*;}
# 3D地图
-dontwarn com.amap.api.mapcore.**
-dontwarn com.amap.api.maps.**
-dontwarn com.autonavi.amap.mapcore.**
-keep class com.amap.api.mapcore.**{*;}
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.amap.mapcore.**{*;}
# 定位
-dontwarn com.amap.api.location.**
-dontwarn com.aps.**
-keep class com.amap.api.location.**{*;}
-keep class com.aps.**{*;}
# 导航
-dontwarn com.amap.api.navi.**
-dontwarn com.autonavi.**
-keep class com.amap.api.navi.** {*;}
-keep class com.autonavi.** {*;}


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


### greenDAO 2
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties


### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
        94    }
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**


# Gson
-keepattributes Signature-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
# 将下面替换成自己的实体类
-keep class com.example.bean.** { *; }


# Jackson
-dontwarn org.codehaus.jackson.**
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.jackson.** { *;}
-keep class com.fasterxml.jackson.** { *; }


# 极光推送
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }


# OkHttp
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**


# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**


# Okio
-dontwarn com.squareup.**  
-dontwarn okio.**  
-keep public class org.codehaus.* { *; }  
-keep public class java.nio.* { *; }


# OrmLite
-keepattributes *DatabaseField* 
-keepattributes *DatabaseTable* 
-keepattributes *SerializedName*  
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }


# Realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**


# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions


# Retrolambda
-dontwarn java.lang.invoke.*


# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


# Universal-Image-Loader-v1.9.5
-libraryjars libs/universal-image-loader-1.9.5-SNAPSHOT-with-sources.jar
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }


# 微信支付
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}


# 信鸽
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.mid.**  {* ;}
-keepattributes *Annotation*


# 新浪微博
-keep class com.sina.weibo.sdk.* { *; }  
-keep class android.support.v4.* { *; }  
-keep class com.tencent.* { *; }  
-keep class com.baidu.* { *; }  
-keep class lombok.ast.ecj.* { *; }  
-dontwarn android.support.v4.**  
-dontwarn com.tencent.**s  
-dontwarn com.baidu.**  


# 讯飞语音
-dontwarn com.iflytek.**
-keep class com.iflytek.** {*;}


# xUtils3.0
-keepattributes Signature,Annotation
-keep public class org.xutils.** {
public protected *;
}
-keep public interface org.xutils.** {
public protected *;
}
-keepclassmembers class * extends org.xutils.** {
public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {;}
-keepclassmembers @org.xutils.http.annotation. class * {*;}
-keepclassmembers class * {
@org.xutils.view.annotation.Event ;
}


# 银联
-dontwarn com.unionpay.**
-keep class com.unionpay.** { *; }


# 友盟统计分析
-keepclassmembers class * { public <init>(org.json.JSONObject); }
-keepclassmembers enum com.umeng.analytics.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


# 友盟自动更新
-keepclassmembers class * { public <init>(org.json.JSONObject); }
-keep public class cn.irains.parking.cloud.pub.R$*{ public static final int *; }
-keep public class * extends com.umeng.**
-keep class com.umeng.** { *; }


# 支付宝钱包
-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*
```


参考博文

[Sam ](http://blog.isming.me/2014/05/31/use-proguard/)

[飞雪无情的博客](http://www.flysnow.org/2015/03/30/manage-your-android-project-with-gradle.html)

[Android Studio 混淆模版](http://www.jianshu.com/p/f9438603e096)



