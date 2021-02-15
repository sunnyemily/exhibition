package cn.org.chtf.card.manage.basic.dao;

import cn.org.chtf.card.manage.basic.pojo.Basic;

public interface BasicMapper {
    int addBasic(Basic record);
    Basic getBasicByMenuId(Integer menuId);
    int deleteBasicByMenuId(Integer menuId);
    int updateBasicByMenuId(Basic basic);
}