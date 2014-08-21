import com.xiayule.workwithserver.model.Person;
import com.xiayule.workwithserver.model.Project;
import com.xiayule.workwithserver.model.Task;
import com.xiayule.workwithserver.model.TaskType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sun.security.pkcs11.P11Util;

import java.util.Date;
import java.util.Set;

/**
 * Created by tan on 14-8-6.
 */
public class test {
    public static void main(String[] args) {
        /*Project.TeamRequestData req = new Project.TeamRequestData();

        req.setStatus("ok");

        Project project = new Project();
        project.setId(1);
        project.setProjectName("熊猫不烧香");
        project.setProjectDesc("齐鲁软件设计大赛");
        project.setProjectOwnerId(1);
        project.setCreateTime(new Date());

        req.addProject(project);

        System.out.println(new Gson().toJson(req));*/

    /*    Configuration conf = new Configuration().configure();
        SessionFactory sf = conf.buildSessionFactory();
        Session sess = sf.openSession();
        Transaction tx = sess.beginTransaction();

        Project project1 = new Project();
        project1.setProjectName("xiongmao");
        project1.setProjectDesc("qilu");
        project1.setCreateTime(new Date());

        Project project2 = new Project();
        project2.setProjectName("benniao");
        project2.setProjectDesc("qiluruanjian");
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
        person.addProject(project1);
        person.addProject(project2);

        sess.persist(person);

        tx.commit();

        Person p = (Person) sess.get(Person.class, 1);

        System.out.println(p);

        sess.close();*/

        System.out.println(TaskType.valueOf("TODO"));

    }
}
