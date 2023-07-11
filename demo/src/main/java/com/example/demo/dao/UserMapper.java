package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(@Param("id") int id);

    @Select("SELECT * FROM user WHERE account = #{account} and password=#{password}")
    User getUserByUsername(@Param("account") String account,@Param("password") String password);

    @Select("SELECT * FROM user WHERE account = #{account}")
    User getUserToken(@Param("account") String account);
    
    @Insert("INSERT INTO user (account, password,role) VALUES (#{account}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int registerUser(User user);

//    @Update("UPDATE users SET token = #{token} WHERE id = #{id}")
//    int updateUserToken(@Param("id") int id, @Param("token") String token);
//
//    @Update("UPDATE users SET token = NULL WHERE id = #{id}")
//    int clearUserToken(@Param("id") int id);


    // 其他的查询、插入、更新、删除方法可以根据需要添加
}