package com.xiayule.workwithclient.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.adapter.TaskListAdapter;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.TaskDetailActivity;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        TaskType.TODO, TaskType.NOW, TaskType.FUTURE
    };


    private TaskType taskType;

//    @InjectView(R.id.listView)
//    @InjectView(android.R.id.list)
//    private ListView listView;

    @InjectView(R.id.gridview)
    GridView gridview;

    private TaskListAdapter taskListAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskType Parameter 1.
     * @return A new instance of fragment TaskFragment.
     */
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

 /*   @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ToastUtils.showShort("click");
        System.out.println("list item click");
    }
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        ButterKnife.inject(this, view);
//        listView = (ListView) view.findViewById(android.R.id.list);
//        gridview = (GridView) view.findViewById(R.id.gridview);

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

        /* simpleadapter 测试
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        for (Task task : showTasks) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("title", task.getTaskName());
            data.put("desc", task.getTaskDesc());
            datas.add(data);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                datas,
                R.layout.task_item,
                new String[] {"title", "desc"},
                new int[] {R.id.title, R.id.desc}
                );*/

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Task task = taskListAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                // 封装要传递数据
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", task);

                intent.putExtras(bundle);
                // 启动 TaskDetailActivity
                startActivity(intent);
            }
        });


        /*listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ToastUtils.showLong("long click");
                return false;
            }
        });*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
