package com.xiayule.workwithclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.util.TimeUtils;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tan on 14-8-9.
 * 任务ListView 的 Adapter
 */
public class TaskListAdapter extends BaseAdapter {
    private List<Task> tasks;

    private Context context;
    private LayoutInflater inflater;

    public TaskListAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CacheView cacheView = null;

        if (view == null) {
            view = inflater.inflate(R.layout.task_item, null);

            cacheView = new CacheView(view);

            view.setTag(cacheView);
        } else {
            cacheView = (CacheView) view.getTag();
        }

        Task task = getItem(position);

        // 显示 标题
        cacheView.title.setText(task.getTaskName());

        // 设置 描述
        String desc = task.getTaskDesc();
        if (desc != null && !desc.equals("")) {
            cacheView.desc.setText(task.getTaskDesc());
            cacheView.desc.setVisibility(View.VISIBLE);
        } else {
            cacheView.desc.setVisibility(View.GONE);
        }

        // 显示时间
        //String time = DateUtils.getRelativeTimeSpanString(task.getEndTime().getTime()).toString();

        Date t = task.getEndTime();
        if (t != null) {
            if (task.isOverDeadline()) {// 如果超期了
                Drawable drawable = context.getResources().getDrawable(R.drawable.tv_shape_red);
                cacheView.time.setBackgroundDrawable(drawable);

                // 设置背景为完成时的背景
//                Drawable backDrawable = context.getResources().getDrawable(R.drawable.projects_item_finished_selector);
//                cacheView.ll_task_item.setBackgroundDrawable(backDrawable);

            } else {// 如果没超期
                Drawable drawable = context.getResources().getDrawable(R.drawable.tv_shape_green);
                cacheView.time.setBackgroundDrawable(drawable);
            }

            String time = TimeUtils.format(task.getEndTime());
            cacheView.time.setText(time);
            cacheView.time.setVisibility(View.VISIBLE);
        } else {
            cacheView.ll_time.setVisibility(View.GONE);
        }

        cacheView.complete.setChecked(task.isComplete());

        return view;
    }

    /**
     * 详情参见 Butter Knife
     */
    class CacheView {
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.desc) TextView desc;
        @InjectView(R.id.time) TextView time;
        @InjectView(R.id.complete) CheckBox complete;

        @InjectView(R.id.ll_time)
        LinearLayout ll_time;

        @InjectView(R.id.ll_task_item)
        LinearLayout ll_task_item;

        public CacheView(View view) {
            ButterKnife.inject(this, view);
        }
    }

    // set and get methods

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
