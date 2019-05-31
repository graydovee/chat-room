package cn.graydove.talk.service.impl;

import cn.graydove.talk.mapper.UserMapper;
import cn.graydove.talk.pojo.User;
import cn.graydove.talk.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        return userMapper.insUser(user);
    }

    @Override
    public User login(User user) {
        return userMapper.selUserByUsernameAndPassword(user);
    }

    @Override
    public boolean isUserExist(String username) {
        if(userMapper.selNicknameByUsername(username)==null)
            return false;
        return true;
    }
}
