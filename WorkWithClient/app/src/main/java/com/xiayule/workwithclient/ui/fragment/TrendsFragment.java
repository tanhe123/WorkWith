package com.xiayule.workwithclient.ui.fragment;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.adapter.TaskListAdapter;
import com.xiayule.workwithclient.adapter.TrendsListAdapter;
import com.xiayule.workwithclient.api.Constants;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.ui.TaskDetailActivity;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrendsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Person mPerson;

    private BroadcastReceiver broadcastReceiver;

    private TrendsListAdapter trendsListAdapter;

    @InjectView(R.id.gridview)
    GridView gridView;

    @InjectView(R.id.null_result)
    TextView tv_null_result;

    private void registerUpdatePersonBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_QUERY_PERSON);

        // 如果修改了任务，也要重新更新下
        intentFilter.addAction(Constants.ACTION_UPDATE_TASK);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateShow();
            }
        };



        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    private void updateShow() {
        mPerson = (Person) App.get(App.PERSON);

        if (mPerson == null) {
            tv_null_result.setVisibility(View.VISIBLE);
            return ;
        }

        // 添加所有过期的任务
        List<Task> tasks = new ArrayList<Task>();
        for (Project project : mPerson.getProjects()) {
            for (Task task : project.getTasks()) {
                if (task.isOverDeadline() && !task.isComplete()) {
                    tasks.add(task);
                }
            }
        }

        if (tasks.size() == 0) {
            tv_null_result.setVisibility(View.VISIBLE);
            return ;
        } else {
            tv_null_result.setVisibility(View.GONE);
        }

//                ToastUtils.showShort("broadcast" + " ;" + getActivity());

        if (getActivity() != null) {
            trendsListAdapter = new TrendsListAdapter(getActivity(), tasks);
            gridView.setAdapter(trendsListAdapter);

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public static TrendsFragment newInstance() {
        TrendsFragment fragment = new TrendsFragment();

        return fragment;
    }
    public TrendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trends, container, false);
        ButterKnife.inject(this, view);

        updateShow();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Task task = trendsListAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);

                // 封装要传递数据
                App.put(App.TASK, task);

                // 启动 TaskDetailActivity
                startActivityForResult(intent, 110);
                getActivity().overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
            }
        });

        return view;
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 110 && resultCode == 1) {
            fresh();
        }
    }*/

    public void fresh() {
        updateShow();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        registerUpdatePersonBroadcast();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        getActivity().unregisterReceiver(broadcastReceiver);
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
