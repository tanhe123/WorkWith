package com.xiayule.workwithserver.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by tan on 14-8-6.
 */
public class Project implements Serializable {
    private Integer id;
    private String projectName;
    private Integer projectOwnerId;
    private Date createTime;
    private String projectDesc;

    // the todoa task
    private List<Task> tasks = new ArrayList<Task>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public static class TeamRequestData {
        public String status;
        public ArrayList<Project> data = new ArrayList<Project>();

        public void addTeam(Project project) {
            if (data == null) {
                data = new ArrayList<Project>();
            }

            data.add(project);
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArrayList<Project> getData() {
            return data;
        }

        public void setData(ArrayList<Project> data) {
            this.data = data;
        }
    }

    @Override
    public String toString() {
        StringBuilder rs = new StringBuilder();
        rs.append("id:" + id + ";projectName:" + projectName + ";projectDesc:" + projectDesc + ";projectOwnerId" + projectOwnerId + ";createTime:" + createTime + ";");
        rs.append("tasks:(");
        for (Task task : tasks) {
            rs.append(task.toString() + ";");
        }
        rs.append(")");
        return rs.toString();
    }

    // get and set methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectOwnerId() {
        return projectOwnerId;
    }

    public void setProjectOwnerId(Integer projectOwnerId) {
        this.projectOwnerId = projectOwnerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;

    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
