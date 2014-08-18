package com.xiayule.workwithserver.dao;

import com.xiayule.workwithserver.model.Task;

import java.util.List;

/**
 * Created by tan on 14-8-15.
 */
public interface TaskDao {
    public Task get(Integer id);

    public Integer save(Task task);

    public void update(Task task);

    public void saveOrUpdate(Task task);

    public void delete(Integer id);

    public void delete(Task task);

    public List<Task> findByName(String name);
}
