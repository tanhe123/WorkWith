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

    public boolean isExist(String username);

    /**
     * 登录，如果返回空则登录失败，否则，返回数据库保存的对象
     * @return
     */
    public Person login(String username, String password);

    public Person get(String username);
}
