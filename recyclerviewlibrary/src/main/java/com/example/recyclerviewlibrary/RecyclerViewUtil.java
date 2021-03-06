package com.example.recyclerviewlibrary;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by steve    on  2018/8/27 0027.
 * 加入分割线
 */

public class RecyclerViewUtil {
    public static int DEFAULT_DIVIDER_DRAWABLE = R.drawable.shape_default_divider;

    /**
     * 重新配置默认的水平分割线资源文件
     *
     * @param drawableId
     */
    public static void reinitDefaultDiveder(int drawableId) {
        DEFAULT_DIVIDER_DRAWABLE = drawableId;
    }

    /**
     * 添加默认的水平分割线
     *
     * @param recyclerView
     */
    public static void addDefaultDivider(RecyclerView recyclerView) {

        DividerItemDecoration decoration =
            new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        Drawable drawable = recyclerView.getResources().getDrawable(DEFAULT_DIVIDER_DRAWABLE);
        decoration.setDrawable(drawable);
        recyclerView.addItemDecoration(decoration);
        
    }

    /**
     * 添加分割线
     *
     * @param recyclerView
     * @param linearLayoutManager
     * @param dividerDrawable 分割线的资源文件
     */
    public static void addDivider(RecyclerView recyclerView,
        LinearLayoutManager linearLayoutManager, Drawable dividerDrawable) {
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),
            linearLayoutManager.getOrientation());
        decoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(decoration);
    }

    /**
     * 添加分割线
     *
     * @param recyclerView
     * @param linearLayoutManager
     * @param drawableResId 分割线的资源文件
     */
    public static void addDivider(RecyclerView recyclerView,
        LinearLayoutManager linearLayoutManager, int drawableResId) {
        Drawable drawable = recyclerView.getResources().getDrawable(drawableResId);
        addDivider(recyclerView, linearLayoutManager, drawable);
    }

    /**
     * 添加item间的间距
     *
     * @param recyclerView
     * @param layoutManager
     * @param space 间距大小，像素
     */
    public static void addSpace(RecyclerView recyclerView, LinearLayoutManager layoutManager,
        int space) {

        RecyclerView.ItemDecoration decoration;
        if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            decoration = new HorizontalSpace(space);
        } else {
            decoration = new VerticalSpace(space);
        }
        recyclerView.addItemDecoration(decoration);
    }

    /**
     * 添加item间的间距
     *
     * @param recyclerView
     * @param layoutManager 布局管理器，添加的间距根据布局管理器的方向而定
     * @param spaceResId 间距尺寸资源文件
     */
    public static void addSpaceByRes(RecyclerView recyclerView, LinearLayoutManager layoutManager,
        @DimenRes int spaceResId) {
        addSpace(recyclerView, layoutManager,
            recyclerView.getResources().getDimensionPixelSize(spaceResId));
    }

    /**
     * 添加item间的间距
     *
     * @param recyclerView
     * @param layoutManager GridLayoutManager
     * @param horizontalSpace 水平间距，像素
     * @param verticalSpace 垂直间距，像素
     */
    public static void addSpace(RecyclerView recyclerView, GridLayoutManager layoutManager,
        int horizontalSpace, int verticalSpace) {
        int spanCount = layoutManager.getSpanCount();
        SpacesItemDecoration itemDecoration =
            SpacesItemDecoration.newInstance(horizontalSpace, verticalSpace, spanCount);
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 添加item间的间距
     *
     * @param recyclerView
     * @param layoutManager
     * @param horizontalSpaceRes
     * @param verticalSpaceRes
     */
    public static void addSpaceByRes(RecyclerView recyclerView, GridLayoutManager layoutManager,
        int horizontalSpaceRes, int verticalSpaceRes) {

        Resources resources = recyclerView.getResources();

        int horizontalSpace =
            horizontalSpaceRes == 0 ? 0 : resources.getDimensionPixelSize(horizontalSpaceRes);
        int verticalSpace =
            verticalSpaceRes == 0 ? 0 : resources.getDimensionPixelSize(verticalSpaceRes);

        addSpace(recyclerView, layoutManager, horizontalSpace, verticalSpace);
    }

    /**
     * 设置嵌套滑动
     *
     * @param recyclerView
     * @param enable
     */
    public static void setNestedScrolling(RecyclerView recyclerView, boolean enable) {
        ViewCompat.setNestedScrollingEnabled(recyclerView, enable);
    }

    //限制加载的item数量,加载到一定数量就变成滑动
    public void setLayoutManager(RecyclerView recyclerView, Activity activity, final int num) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                int widthSpec, int heightSpec) {
                int count = state.getItemCount();
                if (count > 0) {
                    if (count > num) {
                        count = num;
                    }
                    int realHeight = 0;
                    int realWidth = 0;
                    for (int i = 0; i < count; i++) {
                        View view = recycler.getViewForPosition(0);
                        if (view != null) {
                            measureChild(view, widthSpec, heightSpec);
                            int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                            int measuredHeight = view.getMeasuredHeight();
                            realWidth = realWidth > measuredWidth ? realWidth : measuredWidth;
                            realHeight += measuredHeight;
                        }
                        setMeasuredDimension(realWidth, realHeight);
                    }
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
    }

    /**
     * 水平方向的空白
     */
    public static class HorizontalSpace extends RecyclerView.ItemDecoration {

        private int space;

        public HorizontalSpace(int space) {
            this.space = space;
        }

        public HorizontalSpace(Resources resources, int dimenResId) {
            this.space = resources.getDimensionPixelSize(dimenResId);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {

            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.left = space;
            }
        }
    }
    
    /**
        * 垂直方向的空白
        */
    public static class VerticalSpace extends RecyclerView.ItemDecoration {

        private int space;

        public VerticalSpace(int space) {
            this.space = space;
        }

        public VerticalSpace(Resources resources, int dimenResId) {
            this.space = resources.getDimensionPixelSize(dimenResId);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {

            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = space;
            }
        }
    }

}
