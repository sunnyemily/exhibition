package cn.org.chtf.card.manage.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.PageModel;
public interface UserDAO {

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String username);
    
    User login(Map<String,Object> token);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    void updateFailCount(String username);
    void clearFailCount(String username);
    int updatePassword(User user);

    
    List<User> getUsers(PageModel page);
    
    int getTotal(PageModel page);
    
    int updateUser(User user);
    
    int addUser(User user);
    
    int deleteUsers(@Param("usernameList") List<String> usernameList);

	void update(User user);

}