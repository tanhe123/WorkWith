package com.xiayule.workwithserver.util;

import com.xiayule.workwithserver.model.Project;

import java.util.List;

/**
 * Created by tan on 14-8-15.
 */
public class Result {
    private String status;

    private String message;

    private List<Project> projects;

    // get and set methods

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
