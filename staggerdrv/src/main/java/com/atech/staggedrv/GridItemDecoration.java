package com.atech.staggedrv;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * 设置间距
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration{

    private Context context;
    private int interval;

    public GridItemDecoration(Context context, int interval) {
        this.context = context;
        this.interval = interval;
    }

    @Override
    public void getItemOffsets( Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int index = parent.getChildAdapterPosition(view);

        int interval = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.interval, context.getResources().getDisplayMetrics());

        outRect.top = interval;
        outRect.left = interval/2;
        outRect.right = interval/2;
        outRect.bottom = interval;


    }
}
