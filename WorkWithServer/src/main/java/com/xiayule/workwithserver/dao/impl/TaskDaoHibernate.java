package com.xiayule.workwithserver.dao.impl;

import com.xiayule.workwithserver.dao.TaskDao;
import com.xiayule.workwithserver.model.Task;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 14-8-11.
 */
public class TaskDaoHibernate extends HibernateDaoSupport implements TaskDao {

    public Task get(Integer id) {
        return getHibernateTemplate()
                .get(Task.class, id);
    }

    public Integer save(Task task) {
        return (Integer) getHibernateTemplate()
                .save(task);
    }

    @Override
    public void update(Task task) {
        getHibernateTemplate().update(task);
    }

    public void saveOrUpdate(Task task) {
        getHibernateTemplate().saveOrUpdate(task);
    }

    @Override
    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    @Override
    public void delete(Task task) {
        getHibernateTemplate().delete(task);
    }

    @Override
    public List<Task> findByName(String name) {
        return (List<Task>)getHibernateTemplate()
                .find("from Task t where t.taskName = ?", name);
    }
}
