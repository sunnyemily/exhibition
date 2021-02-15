package cn.org.chtf.card.manage.menu.dao;

import java.util.List;

import cn.org.chtf.card.manage.menu.pojo.Menu;

public interface MenuMapper {
	
    int deleteByPrimaryKey(Integer menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    Menu getMenuByName(String menuName);
    
    List<Menu>selectAll();
    
    List<Menu>selectChilds(Integer menuId);
    
    List<Menu>selectBrotherMenu(Integer menuId);
    
    List<Menu> selectLanguageMenu(String languageName);
}