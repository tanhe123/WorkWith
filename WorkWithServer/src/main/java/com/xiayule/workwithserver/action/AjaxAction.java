package com.xiayule.workwithserver.action;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.xiayule.workwithserver.model.Person;
import com.xiayule.workwithserver.model.Project;
import com.xiayule.workwithserver.model.Task;
import com.xiayule.workwithserver.service.PersonService;
import com.xiayule.workwithserver.service.ProjectService;
import com.xiayule.workwithserver.service.TaskService;
import com.xiayule.workwithserver.util.Result;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tan on 14-8-6.
 */
public class AjaxAction {
    private PersonService personService;
    private TaskService taskService;
    private ProjectService projectService;

    private JSONObject json;

    private Person person;
    private String name;

    private String username;
    private String password;

    private String projectName;

    private int projectId;
    private int requestId;

    public String getPersonDo() {
        /*Project project1 = new Project();
//        project1.setId(1);
        project1.setProjectName("熊猫不烧香");
        project1.setProjectDesc("齐鲁软件设计大赛");
//        project1.setProjectOwnerId(1);
        project1.setCreateTime(new Date());

        Project project2 = new Project();
        project2.setProjectName("笨鸟");
        project2.setProjectDesc("齐鲁软件设计大赛");
//        project2.setProjectOwnerId(1);
        project2.setCreateTime(new Date());

        Task task1 = new Task();
        task1.setEndTime(new Date());
        task1.setTaskDesc("准备做什么");
        task1.setTaskName("齐鲁");
        task1.setTaskType(TaskType.TODO);

        Task task2 = new Task();
        task2.setEndTime(new Date());
        task2.setTaskDesc("准备怎么做");
        task2.setTaskName("设计");
        task2.setTaskType(TaskType.NOW);

        Task task3 = new Task();
        task3.setEndTime(new Date());
        task3.setTaskDesc("完成美化工作");
        task3.setTaskName("完成");
        task3.setTaskType(TaskType.FUTURE);

        project1.addTask(task1);
        project1.addTask(task2);
        project1.addTask(task3);

        // person
        Person person = new Person();
        person.setUsername("tanhe123");
        person.setPassword("123");
        person.setName("tan");
        person.addProject(project1);
        person.addProject(project2);

        personService.savePerson(person);*/

        /*
        Person.PersonRequestData data = new Person.PersonRequestData();
        data.setStatus("ok");
        data.setPerson(person);*/

   /*     Configuration conf = new Configuration().configure();
        SessionFactory sf = conf.buildSessionFactory();
        Session sess = sf.openSession();

        Person person = (Person) sess.get(Person.class, 1);*/

        String user = username;

        //todo: 密码暂且不用
        String pass = password;

        // 从数据库中获取 信息
//        Person person = personService.getPerson(1);
        Person person = personService.get(username);

        Person.PersonRequestData data = new Person.PersonRequestData();
        data.setStatus("ok");
        data.setPerson(person);

        json = format(data);

        return Action.SUCCESS;
    }

    public String updatePersonDo() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String method = request.getParameter("method");

        if (method.equals("person")) {// 更新 person
            // 取得传来的 json
            String per = request.getParameter("person");

            // 将 json 转换为 Person 对象
            Person p = new Gson().fromJson(per, Person.class);

            // 更新 person 对象
//            personService.updatePerson(p);
            personService.saveOrUpdatePerson(p);

        } else if (method.equals("project")) {// 更新 project
            String pro = request.getParameter("project");

            System.out.println(pro);

            Project project = new Gson().fromJson(pro, Project.class);

//            projectService.updateProject(project);
            projectService.saveOrUpdateProject(project);

        } else if (method.equals("task")) { // 更新 task
            String ta = request.getParameter("task");

            System.out.println(ta);

            Task task = new Gson().fromJson(ta, Task.class);

//            taskService.updateService(task);

            taskService.saveOrUpdateTask(task);

        /*    Task task = new Task();
            task.setTaskType(TaskType.NOW);
            task.setTaskName("test");
            task.setCreateTime(new Date());
            task.setEndTime(new Date());
            task.setComplete(false);
            task.setTaskDesc("this is just a test");

            taskService.saveTask(task);*/

//            Task task = taskService.getTask(4);
//            task.setTaskDesc("oohh");
//            task.setComplete(true);
//            taskService.updateService(task);

        }

        /*Project project1 = new Project();
//        project1.setId(1);
        project1.setProjectName("熊猫不烧香");
        project1.setProjectDesc("齐鲁软件设计大赛");
//        project1.setProjectOwnerId(1);
        project1.setCreateTime(new Date());

        Project project2 = new Project();
        project2.setProjectName("笨鸟");
        project2.setProjectDesc("齐鲁软件设计大赛");
//        project2.setProjectOwnerId(1);
        project2.setCreateTime(new Date());

        Task task1 = new Task();
        task1.setEndTime(new Date());
        task1.setTaskDesc("准备做什么");
        task1.setTaskName("齐鲁");
        task1.setTaskType(TaskType.TODO);

        Task task2 = new Task();
        task2.setEndTime(new Date());
        task2.setTaskDesc("准备怎么做");
        task2.setTaskName("设计");
        task2.setTaskType(TaskType.NOW);

        Task task3 = new Task();
        task3.setEndTime(new Date());
        task3.setTaskDesc("完成美化工作");
        task3.setTaskName("完成");
        task3.setTaskType(TaskType.FUTURE);

        project1.addTask(task1);
        project1.addTask(task2);
        project1.addTask(task3);

        // person
        Person person = new Person();
        person.setName("tan");
        person.addProject(project1);
        person.addProject(project2);

        personService.savePerson(person);*/

        /*
        Person.PersonRequestData data = new Person.PersonRequestData();
        data.setStatus("ok");
        data.setPerson(person);*/

        // 设置返回结果
        Result result = new Result();
        result.setStatus("ok");

        json = format(result);

        return Action.SUCCESS;
    }

    public String registerDo() {
        String user = username;
        String pass = password;

        String name = getName();

        System.out.println(user + " " + pass + " " + name);

        Person newPerson = new Person();
        newPerson.setName(name);
        newPerson.setUsername(user);
        newPerson.setPassword(pass);

        Result result = new Result();

        if (!personService.isExist(newPerson.getUsername())) {// 如果用户名不存在

            // 保存到数据库
            personService.savePerson(newPerson);

            result.setStatus("ok");

        } else {// 如果用户名存在
            result.setStatus("error");
            result.setMessage("用户名已存在");
        }

        json = format(result);

        return Action.SUCCESS;
    }

    public String loginDo() {
        String user = username;
        String pass = password;

        Person p = personService.login(user, pass);

        // 设置返回结果
        Result result = new Result();

        if (p != null) {
            result.setStatus("ok");
        } else {
            result.setStatus("error");
            result.setMessage("用户名或密码错误");
        }

        json = format(result);

        return Action.SUCCESS;
    }

    public String searchProject() {

        System.out.println(projectName);

        List<Project> projects = projectService.findProjectByName(projectName);


        Result result = new Result();

        if (projects.size() == 0) {
            result.setStatus("error");
        } else {
            result.setStatus("ok");
        }

        result.setProjects(projects);

        json = format(result);

        return Action.SUCCESS;
    }

    public String joinProject() {
        //TODO:
        System.out.println(requestId + " " + projectId + " " + password);



        return Action.SUCCESS;
    }

    public JSONObject format(Object obj) {
        String strJson = new Gson().toJson(obj);

        return JSONObject.fromObject(strJson);
    }

    // get and set methods

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
