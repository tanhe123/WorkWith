package com.xiayule.workwithclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiayule.workwithclient.R;


import com.xiayule.workwithclient.model.Project;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tan on 14-8-21.
 */
public class ProjectsListAdapter extends BaseAdapter {
    private List<Project> projects;

    private Context context;

    private LayoutInflater inflater;

    public ProjectsListAdapter(Context context, List<Project> projects) {
        this.context = context;

        this.projects = projects;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int i) {
        return projects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CacheView cacheView = null;

        if (view == null) {
            view = inflater.inflate(R.layout.projects_item, null);

            cacheView = new CacheView(view);

            view.setTag(cacheView);
        } else {
            cacheView = (CacheView) view.getTag();
        }

        Project project = (Project)getItem(i);

        // 显示 标题
        cacheView.title.setText(project.getProjectName());
        cacheView.desc.setText(project.getProjectDesc());

        return view;
    }

    class CacheView {
        @InjectView(R.id.title)
        TextView title;

        @InjectView(R.id.desc)
        TextView desc;

        public CacheView(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
