package com.xiayule.workwithclient.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.adapter.ProjectsListAdapter;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectsFragment extends Fragment {
    @InjectView(R.id.gridview)
    GridView gridView;

    private List<Project> projects;

    private OnFragmentInteractionListener mListener;

    public static ProjectsFragment newInstance() {
        ProjectsFragment fragment = new ProjectsFragment();

        return fragment;
    }

    public ProjectsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        ButterKnife.inject(this, view);


        projects = mListener.getProjects();

        final ProjectsListAdapter projectsListAdapter = new ProjectsListAdapter(getActivity(), projects);

        gridView.setAdapter(projectsListAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String projectName = projects.get(position).getProjectName();

                mListener.setCaegory(projectName);
            }
        });

        return view;
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

    public interface OnFragmentInteractionListener {
        public  List<Project> getProjects();
        public void setCaegory(String category);
    }

}
