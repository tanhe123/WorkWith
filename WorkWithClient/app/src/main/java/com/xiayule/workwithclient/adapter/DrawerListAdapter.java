package com.xiayule.workwithclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tan on 14-8-25.
 */
public class DrawerListAdapter extends BaseAdapter {
    private List<String> items;

    private String[] initTitles;

    private Context context;

    private LayoutInflater inflater;

    public DrawerListAdapter(Context context, List<String> items) {
        this.context = context;

        this.items = items;

        inflater = LayoutInflater.from(context);

        // 获取初始的条目，因为他们的图标和项目的图标是不同的
        initTitles = context.getResources().getStringArray(R.array.drawer_item_array);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CacheView cacheView = null;

        if (view == null) {
            view = inflater.inflate(R.layout.drawer_list_item, null);

            cacheView = new CacheView(view);
            cacheView.iv_right_arrow.setVisibility(View.GONE);

            view.setTag(cacheView);
        } else {
            cacheView = (CacheView) view.getTag();
        }

        String itemTitle = (String) getItem(i);

        cacheView.item.setText(itemTitle);

        Drawable drawable = null;
        if (itemTitle.equals(initTitles[0])) {
            drawable = context.getResources().getDrawable(R.drawable.menu_dashboard_grey);
        } else if (itemTitle.equals(initTitles[1])) {
            drawable = context.getResources().getDrawable(R.drawable.menu_project_grey);

        } else {
            drawable = context.getResources().getDrawable(R.drawable.menu_team_grey);
            cacheView.iv_right_arrow.setVisibility(View.VISIBLE);
        }

        drawable.setBounds(0, 0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());

        cacheView.item.setCompoundDrawables(drawable, null, null, null);

        // 显示未完成的项目数, 第一个第二个不是项目名
        if (i>=2) {
            int cnt = 0;

            Person person = (Person) App.get(App.PERSON);
            List<Project> projects = person.getProjects();
            for (Project project : projects) {
                // 找到对应的项目
                if (project.getProjectName().equals(itemTitle)) {

                    // 计算未完成任务的数量
                    List<Task> tasks = project.getTasks();
                    for (Task task : tasks) {
                        if (!task.isComplete())
                            cnt++;
                    }

                    if (cnt > 0) {
                        // 显示
                        cacheView.tv_num.setText(""+cnt);
                        cacheView.tv_num.setVisibility(View.VISIBLE);
                    }

                    break;
                }
            }
        }

        return view;
    }

    class CacheView {
        @InjectView(R.id.item)
        TextView item;

        @InjectView(R.id.ll_content)
        LinearLayout ll_content;

        @InjectView(R.id.right_arrow)
        ImageView iv_right_arrow;

        @InjectView(R.id.num)
        TextView tv_num;

        public CacheView(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
