package com.example.administrator.mycommonlibrarydemo.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.recyclerviewlibrary.BaseRecyclerAdapter;
import com.example.administrator.mycommonlibrarydemo.example.adapter.StudentsListAdapter;
import com.example.administrator.mycommonlibrarydemo.example.bean.Student;
import com.example.recyclerviewlibrary.EmptyRecyclerView;
import com.example.recyclerviewlibrary.RecyclerViewUtil;
import com.example.administrator.mycommonlibrarydemo.util.ToastUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDemoActivity extends AppCompatActivity {

    @BindView(R.id.emptyView)
    FrameLayout mEmptyView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.twinkling_refresh)
    TwinklingRefreshLayout mTwinklingRefresh;
    private Unbinder mBind;
    private List<Student> mStudents;
    private int page = 1;
    private int pagesize = 20;
    private StudentsListAdapter mStudentsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        mBind = ButterKnife.bind(this);
        getAllData();
        initRecyclerView();
    }

    private void getAllData() {
        mStudents = new ArrayList<>();
        mStudents.add(new Student(1,"wuyufeng"));
        mStudents.add(new Student(2,"wuyufeng"));
        mStudents.add(new Student(3,"wuyufeng"));
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager =
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //RecyclerViewUtil.addDefaultDivider(mRecyclerView);
        RecyclerViewUtil.addSpaceByRes(mRecyclerView,gridLayoutManager,R.dimen.normal_20dp,R.dimen.normal_20dp);
        mStudentsListAdapter = new StudentsListAdapter();
        mRecyclerView.setAdapter(mStudentsListAdapter);
        mStudentsListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseRecyclerAdapter.CommonHolder holder,
                int position) {
                ToastUtil.showToastShort(getApplicationContext(),position+"");
            }
        });
        EmptyRecyclerView.bind(mRecyclerView,mEmptyView);
        initRefreshView();
    }

    private void initRefreshView() {
        ProgressLayout mProgressLayout = new ProgressLayout(getApplicationContext());
        mProgressLayout.setColorSchemeResources(R.color.color_999);
        mTwinklingRefresh.setHeaderView(mProgressLayout);
        mTwinklingRefresh.setFloatRefresh(true);
        mTwinklingRefresh.setEnableOverScroll(false);
        mTwinklingRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });
        mTwinklingRefresh.startRefresh();
    }

    public void getData() {

        if (page == 1) {
            mStudentsListAdapter.setNewData(mStudents);
            mTwinklingRefresh.finishRefreshing();
            mTwinklingRefresh.finishLoadmore();
            if (mStudents != null && mStudents.size() != 0) {
                page += 1;
            }
        } else {
            mStudentsListAdapter.appendData(mStudents);
            mTwinklingRefresh.finishLoadmore();
            if (mStudents != null && mStudents.size() != 0) {
                page += 1;
            } else {
                ToastUtil.showToastShort(getApplicationContext(),"没有更多数据");
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
    }
}
