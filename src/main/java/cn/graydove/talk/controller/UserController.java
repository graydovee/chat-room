package cn.graydove.talk.controller;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.service.UserService;
import cn.graydove.talk.token.Token;
import cn.graydove.talk.token.TokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenFactory tokenFactory;

    //123456 md5: e10adc3949ba59abbe56e057f20f883e
    @PostMapping("/login")
    public Token login(User user){
        Token token = null;
        if(user !=null && user.getPassword()!=null && user.getUsername()!=null){
            User u = userService.login(user);

            if(u!=null)
               token = tokenFactory.createToken(u);
        }
        return token;
    }

    @PostMapping("/register")
    public int register(User user){
        int c = 0;
        try {
            c = userService.register(user);
        }catch (Exception e){
        }
        return c;
    }

    @GetMapping("/exist")
    public String isUnameExist(String username){
        if(userService.isUserExist(username))
            return "1";
        else return "0";
    }



}
