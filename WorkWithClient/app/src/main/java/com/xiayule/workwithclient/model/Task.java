package com.xiayule.workwithclient.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tan on 14-8-9.
 */
public class Task {
    private Integer id;
    private String taskName;
    private String taskDesc;
    //TODO: 评论, 是一个 list
    // ....
    private Date createTime;
    private Date endTime;
    private boolean complete;

    private TaskType taskType;

    public Task() {
        createTime = new Date();
        // 默认为未完成
        complete = false;

        // 默认为 在做任务
        taskType = TaskType.NOW;
    }

    @Override
    public String toString() {
        return "taskName:" + taskName + ";taskDesc:" + taskDesc + ";taskType:" + taskType
                + ";taskCreateTime:" + createTime + ";endTime:" + endTime;
    }

    // set and get methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
