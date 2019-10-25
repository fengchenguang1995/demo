package cn.f.demo.mapper;

import cn.f.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

	public List<User> findAll(@Param(value = "id") String id)throws Exception;

	public Integer insertUser(User user)throws Exception;


}
