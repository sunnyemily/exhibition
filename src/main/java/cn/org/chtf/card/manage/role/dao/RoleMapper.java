package cn.org.chtf.card.manage.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.org.chtf.card.manage.role.pojo.Role;
import cn.org.chtf.card.support.util.PageModel;

public interface RoleMapper {
    int deleteByPrimaryKey(@Param("roleIdList") List<Integer> roleIdList);

    int insert(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKey(Role record);
    
    List<Role> getRoles(PageModel page);
    
    int getTotal(PageModel page);
}