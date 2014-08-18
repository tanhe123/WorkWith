package com.xiayule.workwithserver.service.impl;

import com.xiayule.workwithserver.dao.ProjectDao;
import com.xiayule.workwithserver.model.Project;
import com.xiayule.workwithserver.service.ProjectService;

/**
 * Created by tan on 14-8-18.
 */
public class ProjectServiceImpl implements ProjectService {
    private ProjectDao projectDao;

    public Integer saveProject(Project project) {
        return projectDao.save(project);
    }

    public void updateProject(Project project) {
        projectDao.update(project);
    }

    public void saveOrUpdateProject(Project project) {
        projectDao.saveOrUpdate(project);
    }


    // set and get methods


    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
}
