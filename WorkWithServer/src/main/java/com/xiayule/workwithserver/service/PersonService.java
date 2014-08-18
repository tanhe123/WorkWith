package com.xiayule.workwithserver.service;

import com.xiayule.workwithserver.model.Person;

/**
 * Created by tan on 14-8-11.
 */
public interface PersonService {
    public Person getPerson(Integer id);

    public Integer savePerson(Person person);

    public void updatePerson(Person person);

    public void saveOrUpdatePerson(Person person);
}
