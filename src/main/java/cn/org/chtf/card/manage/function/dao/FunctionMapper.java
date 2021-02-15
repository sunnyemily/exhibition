package cn.org.chtf.card.manage.function.dao;

import java.util.List;

import cn.org.chtf.card.manage.function.pojo.Function;

public interface FunctionMapper {
    int deleteByPrimaryKey(Integer functionId);

    int insert(Function record);

    int insertSelective(Function record);

    Function selectByPrimaryKey(Integer functionId);

    int updateByPrimaryKeySelective(Function record);

    int updateByPrimaryKey(Function record);
    
    List<Function>selectAll();
    
    List<Function>selectChilds(Integer functionId);
    
    List<Function>getAuthorizedFunctions(String username);
    Function getFunctionByMenuId(Integer menuId);
}