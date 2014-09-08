package com.xiayule.workwithclient.ui.fragment;

import android.app.Activity;
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

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.adapter.TaskListAdapter;
import com.xiayule.workwithclient.api.Constants;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TaskFragment extends Fragment {
    // 参数有三种值，分别为，要做、在做、待定
    private static final String ARG_TASK_TYPE = "type";

    // 参数的值
    public static final String TASK_TYPE_TODO = TaskType.TODO.name();//"要做";
    public static final String TASK_TYPE_NOW = TaskType.NOW.name();//"在做";
    public static final String TASK_TYPE_FUTURE = TaskType.FUTURE.name();//"待定";

    public static final TaskType[] TASK_TYPES = new TaskType[] {
        TaskType.NOW, TaskType.TODO, TaskType.FUTURE
    };


    private TaskType taskType;

    @InjectView(R.id.gridview)
    GridView gridview;

    private TaskListAdapter taskListAdapter;

    private OnFragmentInteractionListener mListener;

    private BroadcastReceiver broadcastReceiver;

    public static TaskFragment newInstance(String taskType) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_TYPE, taskType);

        fragment.setArguments(args);
        return fragment;
    }

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskType = TaskType.valueOf(getArguments().getString(ARG_TASK_TYPE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        ButterKnife.inject(this, view);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Task task = taskListAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);

                // 封装要传递数据
                App.put(App.TASK, task);

                // 启动 TaskDetailActivity
                startActivityForResult(intent, 100);
                getActivity().overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
            }
        });

        init();

        return view;
    }

    private void init() {
        List<Task> tasks = mListener.getProject().getTasks();

        List<Task> showTasks = new ArrayList<Task>();

        // 挑选出与 fragment 符合 的 task，例如所欲的 todoa，所有的 now，或者是所有的 future
        for (Task task : tasks) {
            if (task.getTaskType().equals(taskType)) {
                showTasks.add(task);
            }
        }

        // 设置 Adapter
        taskListAdapter = new TaskListAdapter(getActivity(), showTasks);
        gridview.setAdapter(taskListAdapter);
    }

    /**
     * 注册 添加task 的广播
     */
    private void registerAddTaskBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_UPDATE_TASK);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 数据更新显示
                init();
            }
        };

        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == 1) {
            taskListAdapter.notifyDataSetChanged();
            ToastUtils.showShort("backing");
        }
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        // 注册广播
        registerAddTaskBroadcast();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public Project getProject();
    }
}
