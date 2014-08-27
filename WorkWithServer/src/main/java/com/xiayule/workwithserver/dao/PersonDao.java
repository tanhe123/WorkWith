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

    /**
     * 根据 username 找出数据库中存在的数量(用来判断唯一性)
     * @param username
     * @return
     */
    public long findNum(String username);

    /**
     * 通过用户名和密码来查找用户，用来登录
     * @param username
     * @param password
     * @return
     */
    public Person findBy(String username, String password);

    public Person findBy(String username);

}
