package com.xiayule.workwithserver.service;

import com.xiayule.workwithserver.model.Project;

import java.util.List;

/**
 * Created by tan on 14-8-18.
 */
public interface ProjectService {
    public Integer saveProject(Project project);

    public void updateProject(Project project);

    public void saveOrUpdateProject(Project project);

    public List<Project> findProjectByName(String projectName);
}
