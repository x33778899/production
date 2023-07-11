package com.example.demo.service.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userMapper.getUserById(id);
	}

	@Override
	public User getUserByUsername(String account,String password) {
		// TODO Auto-generated method stub
		return userMapper.getUserByUsername(account,password);
	}

	@Override
	public int registerUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.registerUser(user);
	}


}
