package com.xiayule.workwithserver.service.impl;

import com.xiayule.workwithserver.dao.PersonDao;
import com.xiayule.workwithserver.model.Person;
import com.xiayule.workwithserver.service.PersonService;

/**
 * Created by tan on 14-8-11.
 */
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;

    public Person getPerson(Integer id) {
        return personDao.get(id);
    }

    public Integer savePerson(Person person) {
        return personDao.save(person);
    }

    public void updatePerson(Person person) {
        personDao.update(person);
    }

    // set and get methods


    public PersonDao getPersonDao() {
        return personDao;
    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }
}
