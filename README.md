# 使用添加依赖
Step 1. Add the JitPack repository to your build file

allprojects {
    repositories {
	...
	
	maven { url 'https://jitpack.io' }
	
  }}

Step 2. Add the dependency

dependencies {

     implementation 'com.github.comeonwyf:CommonLibrary:v1.0.6'	
     
}

# CommonLibrary内容说明：
## httplibrary包中：
    包含网络获取数据框架，demo的app中有example
## recyclerviewlibrary包中：
    包含基础的适配器、加分割线的Util等，demo的app中有example
## serialportlibrary包中：
    包含串口打开管理类
## widgetlibrary包中：
    包含自定义控件：
    ColorSeeBar（多彩seekbar，可设置拖动或不拖动）；
    DifferentTextView(最多三个textview，显示不同字体大小颜色)；
    MoneyEditText(仿微信输入金额的edittext)；
    PayTypeView（通用选择支付方式（微信或支付宝）的控件）；
    RatingBar(星星评价控件)；
    RoundRectImageView(圆角的Imageview)；
    ShoppingSelectNumView(购买商品选择数量的控件)；
    工具类：
    防止按钮快速点击Util；
    十六进制和十进制操作的util；
    Handler弱引用的Util；

