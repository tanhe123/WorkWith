package com.xiayule.workwithclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiayule.workwithclient.R;

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

            view.setTag(cacheView);
        } else {
            cacheView = (CacheView) view.getTag();
        }

        String itemTitle = (String) getItem(i);

        cacheView.item.setText(itemTitle);

        if (itemTitle.equals(initTitles[0])) {
            cacheView.image.setImageResource(R.drawable.menu_dashboard_grey);
        } else if (itemTitle.equals(initTitles[1])) {
            cacheView.image.setImageResource(R.drawable.menu_project_grey);
        } else {
            // todo: 设置合理
            cacheView.ll_content.setPadding(50, 0, 0, 0);

            cacheView.image.setImageResource(R.drawable.menu_team_grey);
        }

        return view;
    }

    class CacheView {
        @InjectView(R.id.icon)
        ImageView image;

        @InjectView(R.id.item)
        TextView item;

        @InjectView(R.id.ll_content)
        LinearLayout ll_content;

        public CacheView(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
