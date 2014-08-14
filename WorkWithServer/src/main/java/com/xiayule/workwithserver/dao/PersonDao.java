package com.xiayule.workwithserver.dao;

import com.xiayule.workwithserver.model.Person;

import java.util.List;

/**
 * Created by tan on 14-8-11.
 */
public interface PersonDao {
    public Person get(Integer id);

    public Integer save(Person person);

    public void update(Person person);

    public void delete(Integer id);

    public void delete(Person person);

    public List<Person> findByName(String name);

    public List findAllPerson();

    public long getPersonNumber();

    public void saveOrUpdate(Person person);

}
