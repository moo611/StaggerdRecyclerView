package com.atech.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atech.staggedrv.GridItemDecoration;
import com.atech.staggedrv.StaggedAdapter;
import com.atech.staggedrv.StaggerdRecyclerView;
import com.atech.staggedrv.callbacks.LoadMoreAndRefresh;
import com.atech.staggedrv.model.StaggedModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    MyAdapter<FakeModel> staggedAdapter;
    StaggerdRecyclerView str;

    private List<FakeModel> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = findViewById(R.id.str);
        staggedAdapter = new MyAdapter<>(this);
        str.link(staggedAdapter,2);

        //动画效果
        str.addAnimation(R.anim.right_to_left);
       //间距
        str.addDecoration(new GridItemDecoration(this,10));

        str.addCallbackListener(new LoadMoreAndRefresh() {
            @Override
            public void onLoadMore() {
                getData(false);
            }

            @Override
            public void onRefresh() {

                getData(true);
            }
        });

        getData(true);

    }


    /**
     * 自定义adapter继承staggedadapter
     */

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


    /**
     * 自定义viewholder
     */

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

        }
    }


    /**
     * 模拟网络请求
     *
     * @param refresh
     */

    private void getData(final boolean refresh) {

         //模拟刷新，只插入一遍数据
        if (refresh){

            if (datas.size()==0){

                datas.add(new FakeModel(500,500,R.drawable.a1));
                datas.add(new FakeModel(500,1000,R.drawable.a2));
                datas.add(new FakeModel(500,750,R.drawable.a3));
                datas.add(new FakeModel(500,530,R.drawable.a4));
                datas.add(new FakeModel(500,400,R.drawable.a5));
                datas.add(new FakeModel(500,980,R.drawable.a6));
                datas.add(new FakeModel(500,600,R.drawable.a7));
                datas.add(new FakeModel(500,620,R.drawable.a8));
                datas.add(new FakeModel(500,680,R.drawable.c1));
                datas.add(new FakeModel(500,705,R.drawable.c2));
                datas.add(new FakeModel(500,885,R.drawable.c3));


            }

            staggedAdapter.refresh(datas);

        }else{

            //模拟加载更多

            datas.add(new FakeModel(500,840,R.drawable.c4));
            datas.add(new FakeModel(500,712,R.drawable.c5));
            datas.add(new FakeModel(500,624,R.drawable.c6));
            datas.add(new FakeModel(500,888,R.drawable.c7));


            staggedAdapter.loadMore(datas);
        }


    }

}
