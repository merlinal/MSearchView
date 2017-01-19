# MSearchView

#### 示例

```
       <com.merlin.view.MSearchView
            android:id="@+id/MSearchView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentRight="true"
            android:layout_centerInParent="true"
            android:layout_margin="4dp"
            app:clearIcon="@drawable/delete"
            app:expanded="false"
            app:fromColor="#0000cd00"
            app:hint="@string/app_name"
            app:hintColor="#ff999999"
            app:searchExpandIcon="@drawable/search_grey"
            app:searchShrinkIcon="@drawable/search_white"
            app:textColor="#00cd00"
            app:toColor="#ffffffff" />
```
#### 说明
expanded：默认是否为展开状态，默认false
<br>searchExpandIcon：展开后的搜索图标
<br>searchShrinkIcon：收缩后的搜索图标
<br>fromColor：从收缩到展开的背景色渐变起始色
<br>toColor：从收缩到展开的背景色渐变结束色
<br>hint：输入框的提示文本
<br>hintColor：输入框提示文本颜色
clearIcon：清除输入框图标。（输入框若有内容，则清楚输入框；若无，收缩。）

#### 效果图
只是截屏，没法看到动画了...
<br><br>
<img src="https://github.com/zhAoAnliN/Images/blob/master/MSearchView/MSerachView1.jpg?raw=true" width = "600" height = "150" align=center />
<br><br>
<img src="https://github.com/zhAoAnliN/Images/blob/master/MSearchView/MSerachView2.jpg?raw=true" width = "600" height = "150" align=center />