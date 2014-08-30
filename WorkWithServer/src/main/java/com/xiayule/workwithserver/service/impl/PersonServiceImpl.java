package com.xiayule.workwithserver.service.impl;

import com.xiayule.workwithserver.dao.PersonDao;
import com.xiayule.workwithserver.model.Person;
import com.xiayule.workwithserver.service.PersonService;

import java.util.List;

/**
 * Created by tan on 14-8-11.
 */
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;

    public Person getPerson(Integer id) {
        return personDao.get(id);
    }


    /**
     * 用户名唯一性
     */
    public boolean isExist(String username) {
        return personDao.findNum(username) != 0;
    }

    /**
     * 登录，如果返回空则登录失败，否则，返回数据库保存的对象
     * @return
     */
    public Person login(String username, String password) {
        return personDao.findBy(username, password);
    }

    public Integer savePerson(Person person) {
        return personDao.save(person);
    }

    public void updatePerson(Person person) {
        personDao.update(person);
    }

    public void saveOrUpdatePerson(Person person) {
        personDao.saveOrUpdate(person);
    }

    public Person get(String username) {
        return personDao.findBy(username);
    }

    // set and get methods

    public PersonDao getPersonDao() {
        return personDao;
    }



    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }
}
