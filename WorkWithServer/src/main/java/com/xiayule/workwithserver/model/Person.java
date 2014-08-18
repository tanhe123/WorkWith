package com.xiayule.workwithserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-8-6.
 */
public class Person {
    private int id;
    private String name;

    // 用户名
    private String username;
    // 密码
    private String password;

    private Set<Project> projects = new HashSet<Project>();

    public Project getProject(String projectName) {
        for (Project project : projects) {
            if (project.getProjectName().equals(projectName)) {
                return project;
            }
        }
        return null;
    }

    public static class PersonRequestData {
        private String status;
        private Person person;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
    }

    public List<String> getProjectNames() {
        ArrayList<String> projectNames = new ArrayList();

        for (Project project : getProjects()) {
            projectNames.add(project.getProjectName());
        }

        return projectNames;
    }

    public void addTeam(Project project) {
        projects.add(project);
    }

    @Override
    public String toString() {
        StringBuilder rs = new StringBuilder();
        rs.append("name:" + name + ";Projects:(");
        for (Project project : projects) {
            rs.append(project.toString() + ";");
        }
        rs.append(")");
        return rs.toString();
    }

    // set and get methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
