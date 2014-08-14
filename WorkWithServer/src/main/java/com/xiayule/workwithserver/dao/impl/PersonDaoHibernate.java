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
                .find("from Person p where p.name = ?", name);
    }

    @Override
    public List findAllPerson() {
        return (List<Person>)getHibernateTemplate().find("from Person");
    }

    @Override
    public long getPersonNumber() {
        return (Long) getHibernateTemplate().find
                ("select count(*) from Person as p")
                .get(0);
    }
}
