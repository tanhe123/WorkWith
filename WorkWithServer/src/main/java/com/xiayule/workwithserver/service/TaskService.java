package com.xiayule.workwithserver.service;

import com.xiayule.workwithserver.model.Task;

/**
 * Created by tan on 14-8-15.
 */
public interface TaskService {

    public Task getTask(Integer id);
    public Integer saveTask(Task task);

    public void updateService(Task task);
}
