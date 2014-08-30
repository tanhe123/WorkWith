package com.xiayule.workwithserver.dao.impl;

import com.xiayule.workwithserver.dao.PersonDao;
import com.xiayule.workwithserver.model.Person;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 14-8-11.
 */
public class PersonDaoHibernate extends HibernateDaoSupport implements PersonDao {

    public Person get(Integer id) {
        return getHibernateTemplate()
                .get(Person.class, id);
    }

    public Integer save(Person person) {
        return (Integer) getHibernateTemplate()
                .save(person);
    }

    @Override
    public void update(Person person) {
        getHibernateTemplate().update(person);
    }

    public void saveOrUpdate(Person person) {
        getHibernateTemplate().saveOrUpdate(person);
    }

    @Override
    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    @Override
    public void delete(Person person) {
        getHibernateTemplate().delete(person);
    }

    @Override
    public List<Person> findByName(String name) {
        return (List<Person>)getHibernateTemplate()
                .find("from as Person p where p.name = ?", name);
    }

    @Override
    public List findAllPerson() {
        return (List<Person>)getHibernateTemplate().find("from Person");
    }

    /**
     * 根据 username 找出数据库中存在的数量(用来判断唯一性)
     * @param username
     * @return
     */
    public long findNum(String username) {
        return (Long) getHibernateTemplate()
                .find("select count(*) from Person as p where p.username = ?", username).get(0);
    }

    /**
     * 通过用户名和密码来查找用户，用来登录
     * @param username
     * @param password
     * @return
     */
    public Person findBy(String username, String password) {
        List<Person> persons = (List<Person>) getHibernateTemplate()
                .find("from Person as p where p.username = ? and p.password = ?",
                        username, password);

        if (persons.size() == 0) {
            return null;
        } else {
            return persons.get(0);
        }
    }

    public Person findBy(String username) {
        List<Person> persons = (List<Person>) getHibernateTemplate()
                .find("from Person as p where p.username = ?",
                        username);

        if (persons.size() == 0) {
            return null;
        } else {
            return persons.get(0);
        }
    }


    @Override
    public long getPersonNumber() {
        return (Long) getHibernateTemplate().find
                ("select count(*) from Person as p")
                .get(0);
    }
}
