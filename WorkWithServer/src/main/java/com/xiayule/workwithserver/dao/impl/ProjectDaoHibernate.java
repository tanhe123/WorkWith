package com.xiayule.workwithserver.dao.impl;

import com.xiayule.workwithserver.dao.PersonDao;
import com.xiayule.workwithserver.dao.ProjectDao;
import com.xiayule.workwithserver.model.Person;
import com.xiayule.workwithserver.model.Project;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 14-8-11.
 */
public class ProjectDaoHibernate extends HibernateDaoSupport implements ProjectDao {

    public Project get(Integer id) {
        return getHibernateTemplate()
                .get(Project.class, id);
    }

    public Integer save(Project project) {
        return (Integer) getHibernateTemplate()
                .save(project);
    }

    @Override
    public void update(Project project) {
        getHibernateTemplate().update(project);
    }

    public void saveOrUpdate(Project project) {
        getHibernateTemplate().saveOrUpdate(project);
    }

    @Override
    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    @Override
    public void delete(Project project) {
        getHibernateTemplate().delete(project);
    }

    @Override
    public List<Project> findByName(String name) {
        return (List<Project>)getHibernateTemplate()
                .find("from Project p where p.projectName like ?", "%"+name+"%");
    }

    @Override
    public List findAllProject() {
        return (List<Project>)getHibernateTemplate().find("from Project");
    }

    @Override
    public long getProjectNumber() {
        return (Long) getHibernateTemplate().find
                ("select count(*) from Project as p")
                .get(0);
    }
}
