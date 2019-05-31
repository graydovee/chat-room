package cn.graydove.talk.service;

import cn.graydove.talk.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    int register(User user);

    User login(User user);

    boolean isUserExist(String username);
}
