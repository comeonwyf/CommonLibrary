package com.example.administrator.mycommonlibrarydemo.example.adapter;

import android.widget.TextView;
import com.example.administrator.mycommonlibrarydemo.R;
import com.example.administrator.mycommonlibrarydemo.base_adapter_recyclerview.BaseRecyclerAdapter;
import com.example.administrator.mycommonlibrarydemo.example.bean.Student;

/**
 * Created by wuyufeng    on  2018/8/27 0027.
 * interface by
 */

public class StudentsListAdapter extends BaseRecyclerAdapter<Student>{
    
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_student;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        Student item = getItem(position);
        TextView name = holder.getText(R.id.tv_name);
        name.setText(item.name);
    }
}
