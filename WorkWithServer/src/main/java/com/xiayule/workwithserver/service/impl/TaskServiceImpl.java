package com.xiayule.workwithserver.service.impl;

import com.xiayule.workwithserver.dao.TaskDao;
import com.xiayule.workwithserver.model.Task;
import com.xiayule.workwithserver.service.TaskService;

/**
 * Created by tan on 14-8-15.
 */
public class TaskServiceImpl implements TaskService {
    private TaskDao taskDao;

    public Integer saveTask(Task task) {
        return taskDao.save(task);
    }

    public void updateService(Task task) {
        taskDao.update(task);
    }

    @Override
    public Task getTask(Integer id) {
        return taskDao.get(id);
    }

    // get and set methods

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
