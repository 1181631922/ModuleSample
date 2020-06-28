# 操作反馈提示类Dialog

## 简介
>ripple_dialog对dialog和toast进行了封装

### 导入sdk
首先需要在根目录的`build.gradle`中导入`maven`地址：
```
maven { url 'https://dl.bintray.com/fanyafeng/ripple' }
```
在module的build.gradle中添加依赖：

```
implementation 'com.ripple.component:dialog:0.0.2'
```
### 一、RippleDialog快速使用
快速接入使用：常用的dialog形式为中心显示和底部显示（这里根据ui提供的规范进行了默认定义，即：中心显示默认使用系统动画；点击外围不解散；背景半透明；相应用户操作进行消失操作，底部显示默认底部弹出动画；点击外围解散；背景半透明；相应用户操作进行消失操作）

1.dialog中心显示：

```
val dialog = RippleDialog(this, view)
dialog.showCenter()
```
2.dialog底部显示：

```
val dialog = RippleDialog(this, view)
dialog.showBottom()
```

### 二、如果需要对RippleDialog进行详细配置
1.RippleDialogConfig(dialog配置类，支持元素get方法)
 * context: Context   上下文
 * themeResId: Int    主题
 * gravity: Int       位置
 * contentView: View  显示view
 * animation: Int     显示消失动画
 * isCancel: Boolean  点击外围是否解散

 sdk中提供一套**themeResId**和一套**animation**分别是：***RippleDialogStyle***，***RippleMenuAnimation***

 2.使用MNDialogConfig进行dialog的配置显示

```
View view = LayoutInflater.from(context).inflate(R.layout.id, null);
final RippleDialog dialog = new RippleDialog(new RippleDialogConfig.Builder()
        .setContext(context)//设置上下文
        .setGravity(Gravity.BOTTOM)//显示位置在底部
        .setContentView(view)
        .setCancel(true)//点击外围解散
        .setAnimation(R.style.RippleMenuAnimation)//sdk中的弹出动画，如过需要其他效果可以进行自定义
        .setThemeResId(R.style.RippleDialogStyle)
        .build());
view.findViewById(R.id.btn_id).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //相应的操作
        dialog.dismiss();
    }
});
dialog.show();
```

### 三、当你需要对dialog或者用户点击进行监听或者拦截
1.拦截返回键

```
dialog.setResponseBackPress(true)
```
2.相应拦截

```
dialog.setOnBackpressListener(new RippleDialog.OnBackPressListener() {
                    @Override
                    public void onBackPress() {
                        // TODO: 18/6/12 相应操作
                        RippleToast.show(this, "拦截返回键");
                    }
                });
```
3.dialog消失监听

```
dialog.setOnDismissListener(new RippleDialog.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        RippleToast.show(this, "dialog消失监听");
                    }
                });
```

### 四、RippleToast快速接入使用
1.中心显示：

```
RippleToast.showCenter(context,"显示文案");
```
2.底部默认显示：

```
RippleToast.show(context,"显示文案");
```
3.如果感觉每次都写context麻烦可以在BaseActivity或BaseFragment自定义一个方法：

```
protected void toast(CharSequence message) {
        RippleToast.show(context, message);
}

protected void toastCenter(CharSequence message) {
        RippleToast.showCenter(context, message);
}
```
4.以上是不带icon，如果想在上面加上icon的话可以这样：

```
RippleToast.showCenter(context,resouseID,"显示文案");

RippleToast.show(context,resouseID,"显示文案");
```
### 五、RippleToast详细使用
1.可以对toast的view，显示时间，位置进行全部自定义

```
RippleToast.show(context, "", view, Toast.LENGTH_SHORT, Gravity.CENTER);

RippleToast.show(context, charSequence, view, duration, gravity, xOffset, yOffset);
```



