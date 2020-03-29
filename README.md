# StaggerdRecyclerView
android瀑布流，完美解决滑动过程中item位置错乱，下拉刷新顶部空白，加载更多顶部错乱等问题


# 效果图
![](https://github.com/moo611/StaggerdRecyclerView/blob/master/images/e2.gif)
![](https://github.com/moo611/StaggerdRecyclerView/blob/master/images/e3.gif)
# 集成
#### 版本
[![](https://jitpack.io/v/moo611/StaggerdRecyclerView.svg)](https://jitpack.io/#moo611/StaggerdRecyclerView)
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
	        implementation 'com.github.moo611:StaggerdRecyclerView:latestversion'
	}
```
# 基本用法

#### 1.布局文件里添加
```xml

<com.atech.staggedrv.StaggerdRecyclerView
    android:id="@+id/str"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>

```

#### 2.您的model，需要实现StaggedModel接口
```java
//示例 example
public class FakeModel implements StaggedModel {
 
    private int width;
    private int height;
    private int resourceId;

    public FakeModel(int width, int height, int resourceId){
        this.width = width;
        this.height = height;
        this.resourceId = resourceId;
    }



    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getThumb() {
        return null;
    }

    @Override
    public int localResorce() {
        return resourceId;
    }
}

```

#### 3.您的adapter，需要继承staggedadapter
```java
  class MyAdapter<T extends StaggedModel> extends StaggedAdapter<T> {

        MyAdapter(Context c) {
            super(c);
        }


        @Override
        public RecyclerView.ViewHolder addViewHolder(ViewGroup viewGroup, int i) {
            //绑定自定义的viewholder
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_item_layout,viewGroup,false);
            return new MyHolder(v);
        }

        @Override
        public void bindView(RecyclerView.ViewHolder viewHolder, int i) {

            MyHolder myHolder = (MyHolder)viewHolder;


            // 在加载图片之前设定好图片的宽高，防止出现item错乱及闪烁
            ViewGroup.LayoutParams layoutParams = myHolder.img.getLayoutParams();
            layoutParams.height = datas.get(i).getHeight();
            myHolder.img.setLayoutParams(layoutParams);

            myHolder.img.setImageResource(datas.get(i).localResorce());

        }


    }
```
#### 4.您的viewholder,和recyclerview时的写法一样。
```java
class MyHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

        }
    }
```


#### 5.mainactivity
```java
MyAdapter<FakeModel> staggedAdapter;
StaggerdRecyclerView str;
private List<FakeModel> datas = new ArrayList<>();
...


        str = findViewById(R.id.str);
        staggedAdapter = new MyAdapter<>(this);
        str.link(staggedAdapter,2);

      
        str.addCallbackListener(new LoadMoreAndRefresh() {
            @Override
            public void onLoadMore() {
                loadMore();//加载更多
            }

            @Override
            public void onRefresh() {

                refresh();//下拉刷新
            }
        });

        refresh();

...

```
# 其他功能
#### 动画效果
```java
str.addAnimation(R.anim.right_to_left);
```

#### 设置间距
```java
str.addDecoration(new GridItemDecoration(this,10));
```

#### 禁止刷新
```java
str.enableRefresh(false);
```

#### 禁止加载更多
```java
str.enableLoadMore(false);
```

# 原理
#### 滑动时位置错乱
在Recyclerview滑动过程中，由于缓存复用机制，会对Item进行重绘, 如果不确定imageview的宽高，就会造成位置错乱和闪烁。
#### 刷新时顶部留白
使用 notifyDataSetChanged()方法做刷新时，会触发StaggeredGridLayoutManager 的onItemsChanged 方法，导致item的spanIndex重现进行计算，item所在列的位置出现了变化，导致了顶部空白。而用notifyItemRangeInserted，notifyItemRangeChanged做局部刷新时则不会引起变化。


# 参考
https://www.jianshu.com/p/d34075c0f287

