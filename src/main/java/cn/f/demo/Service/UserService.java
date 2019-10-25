package cn.f.demo.Service;

import cn.f.demo.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findAll(String id)throws Exception;

    Integer insertUser(List<User> users)throws Exception;

}
