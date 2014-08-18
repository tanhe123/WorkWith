package com.xiayule.workwithserver.action;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.Action;
import com.xiayule.workwithserver.dao.TaskDao;
import com.xiayule.workwithserver.model.Person;
import com.xiayule.workwithserver.model.Project;
import com.xiayule.workwithserver.model.Task;
import com.xiayule.workwithserver.model.TaskType;
import com.xiayule.workwithserver.service.PersonService;
import com.xiayule.workwithserver.service.TaskService;
import com.xiayule.workwithserver.util.Result;
import net.sf.json.JSONObject;
import org.apache.http.HttpRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by tan on 14-8-6.
 */
public class AjaxAction {
    private PersonService personService;
    private TaskService taskService;

    private JSONObject json;

    private Person person;
    private String name;

    public String getPersonDo() {
        /*Project project1 = new Project();
        project1.setId(1);
        project1.setProjectName("熊猫不烧香");
        project1.setProjectDesc("齐鲁软件设计大赛");
        project1.setProjectOwnerId(1);
        project1.setCreateTime(new Date());

        Project project2 = new Project();
        project2.setId(1);
        project2.setProjectName("笨鸟");
        project2.setProjectDesc("齐鲁软件设计大赛");
        project2.setProjectOwnerId(1);
        project2.setCreateTime(new Date());

        Task task1 = new Task();
        task1.setEndTime(new Date());
        task1.setTaskDesc("plan what to do");
        task1.setTaskName("qilu");
        task1.setTaskType(TaskType.NOW);

        Task task2 = new Task();
        task2.setEndTime(new Date());
        task2.setTaskDesc("plan what to do");
        task2.setTaskName("qilu");
        task2.setTaskType(TaskType.NOW);

        project1.addTask(task1);
        project1.addTask(task2);

        // person
        Person person = new Person();
        person.setName("tan");
        person.setId(1);
        person.addTeam(project1);
        person.addTeam(project2);

        Person.PersonRequestData data = new Person.PersonRequestData();
        data.setStatus("ok");
        data.setPerson(person);*/

   /*     Configuration conf = new Configuration().configure();
        SessionFactory sf = conf.buildSessionFactory();
        Session sess = sf.openSession();

        Person person = (Person) sess.get(Person.class, 1);*/

        // 从数据库中获取 信息
        Person person = personService.getPerson(1);

        Person.PersonRequestData data = new Person.PersonRequestData();
        data.setStatus("ok");
        data.setPerson(person);

//        String strJson = new Gson().toJson(data);

//        json = JSONObject.fromObject(strJson);

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
            personService.updatePerson(p);

        } else if (method.equals("project")) {// 更新 project
            // todo: 更新 project
            String pro = request.getParameter("project");
            Project project = new Gson().fromJson(pro, Project.class);

            

        } else if (method.equals("task")) { // 更新 task
            String ta = request.getParameter("task");

            System.out.println(ta);

            Task task = new Gson().fromJson(ta, Task.class);

            taskService.updateService(task);

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
        person.addTeam(project1);
        person.addTeam(project2);

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
}
