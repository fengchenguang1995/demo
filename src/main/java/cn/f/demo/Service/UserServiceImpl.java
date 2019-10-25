package cn.f.demo.Service;

import cn.f.demo.mapper.UserMapper;
import cn.f.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> findAll(String id) throws Exception {
        return userMapper.findAll(id);
    }

    @Override
    public Integer insertUser(List<User> users) throws Exception {
        Integer count=0;
        for (User u:users){
            userMapper.insertUser(u);
            count++;
        }
        return count;
    }
}
