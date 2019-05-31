package cn.graydove.talk.mapper;

import cn.graydove.talk.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where #{username}=username and #{password}=password")
    User selUserByUsernameAndPassword(User user);

    @Select("select nickname from user where username=#{0}")
    String selNicknameByUsername(String username);

    @Insert("insert into user value(default,#{username},#{nickname},#{password})")
    int insUser(User user);
}
