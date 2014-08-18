package com.xiayule.workwithserver.dao;

import com.xiayule.workwithserver.model.Project;

import java.util.List;

public interface ProjectDao {
    public Project get(Integer id);

    public Integer save(Project project);

    public void update(Project project);

    public void saveOrUpdate(Project project);

    public void delete(Integer id);

    public void delete(Project project);

    public List<Project> findByName(String name);

    public List findAllProject();

    public long getProjectNumber();
}
